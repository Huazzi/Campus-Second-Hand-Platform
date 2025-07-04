from sqlalchemy.orm import Session
import pandas as pd
import numpy as np
from typing import List, Dict, Tuple
import logging
from datetime import datetime, timedelta

from app.models import Good, Category, ProductSimilarity, UserView, UserPreference, Collect
from sqlalchemy import func
from app.schemas import RecommendedGood

logger = logging.getLogger(__name__)

def get_content_based_recommendations(
    db: Session,
    user_id: int,
    limit: int = 10,
    offset: int = 0
) -> List[RecommendedGood]:
    """
    基于内容的推荐实现
    根据用户历史浏览、购买行为及商品特征生成推荐
    """
    try:
        # 1. 获取用户偏好
        user_preference = db.query(UserPreference).filter(UserPreference.user_id == user_id).first()
        
        # 2. 获取用户最近浏览的商品
        recent_views = db.query(UserView).filter(
            UserView.user_id == user_id,
            UserView.view_time > (datetime.now() - timedelta(days=30))  # 最近30天浏览的商品
        ).order_by(UserView.view_time.desc()).limit(5).all()
        
        # 如果没有浏览记录且没有偏好记录，返回空列表
        if not recent_views and not user_preference:
            return []
            
        # 3. 构建查询条件
        query = db.query(Good).filter(Good.status == 0, Good.num > 0)
        
        # 添加用户偏好条件
        if user_preference:
            # 偏好的分类
            if user_preference.category_id:
                query = query.filter(Good.category_id == user_preference.category_id)
            # 偏好的价格范围
            if user_preference.price_min:
                query = query.filter(Good.price >= user_preference.price_min)
            if user_preference.price_max:
                query = query.filter(Good.price <= user_preference.price_max)
            # 偏好的位置
            if user_preference.preferred_location:
                query = query.filter(Good.location == user_preference.preferred_location)
        
        # 4. 如果有浏览记录，找出类似商品
        viewed_good_ids = [view.good_id for view in recent_views]
        if viewed_good_ids:
            # 排除已浏览的商品
            query = query.filter(Good.id.notin_(viewed_good_ids))
            
            # 添加类别匹配条件 - 从浏览过的商品中获取类别
            viewed_categories = db.query(Good.category_id).filter(
                Good.id.in_(viewed_good_ids),
                Good.category_id != None
            ).distinct().all()
            
            if viewed_categories:
                category_ids = [cat[0] for cat in viewed_categories]
                query = query.filter(Good.category_id.in_(category_ids))
        
        # 5. 根据浏览次数和询问次数排序（热度排序）
        query = query.order_by((Good.view_count + Good.inquiry_count).desc())
        
        # 6. 分页
        recommendations = query.offset(offset).limit(limit).all()
        
        # 7. 转换为响应格式
        result = []
        for item in recommendations:
            # 使用goods表的collect_count字段，避免实时COUNT查询
            collect_count = getattr(item, 'collect_count', 0) or 0

            # 调试日志：打印商品数据
            logger.info(f"内容推荐数据 - ID:{item.id}, 名称:{item.goodname}, 浏览次数:{item.view_count}, 收藏次数:{collect_count}")

            result.append(
                RecommendedGood(
                    id=item.id,
                    goodname=item.goodname,
                    price=float(item.price),
                    description=item.description,
                    image=item.image,
                    view_count=item.view_count or 0,  # 确保不返回None
                    category_id=item.category_id,
                    similarity_score=None,  # 在基础版本中不计算相似度分数
                    collect=collect_count
                )
            )
        
        return result
        
    except Exception as e:
        logger.error(f"Content-based recommendation error: {str(e)}")
        return []

def update_product_similarities(db: Session):
    """
    更新商品相似度矩阵
    基于商品类别、价格、描述等特征计算相似度
    """
    try:
        # 1. 获取所有有效商品
        goods = db.query(Good).filter(Good.status == 0).all()
        
        # 2. 创建商品特征数据
        goods_data = []
        for good in goods:
            goods_data.append({
                'id': good.id,
                'category_id': good.category_id if good.category_id else 0,
                'price': float(good.price),
                'location': good.location if good.location else '',
                'description': good.description
            })
            
        if not goods_data:
            return
            
        # 3. 转换为DataFrame
        df = pd.DataFrame(goods_data)
        
        # 4. 为每个商品计算与其他商品的简单相似度
        for i, good1 in enumerate(goods_data):
            for j, good2 in enumerate(goods_data):
                if i >= j:  # 跳过自己和之前已比较的配对
                    continue
                    
                # 计算简单相似度
                similarity_score = 0.0
                
                # 类别相同得分高
                if good1['category_id'] == good2['category_id'] and good1['category_id'] > 0:
                    similarity_score += 0.5
                
                # 价格相近得分高（价差在20%以内）
                price1, price2 = good1['price'], good2['price']
                if price1 > 0 and price2 > 0:
                    price_diff = abs(price1 - price2) / max(price1, price2)
                    if price_diff < 0.2:
                        similarity_score += (1 - price_diff) * 0.3
                
                # 位置相同得分高
                if good1['location'] and good2['location'] and good1['location'] == good2['location']:
                    similarity_score += 0.2
                
                # 保存相似度得分（只保存得分大于0.3的）
                if similarity_score > 0.3:
                    # 检查是否已存在记录
                    existing = db.query(ProductSimilarity).filter(
                        ((ProductSimilarity.good_id_1 == good1['id']) & 
                         (ProductSimilarity.good_id_2 == good2['id'])) |
                        ((ProductSimilarity.good_id_1 == good2['id']) & 
                         (ProductSimilarity.good_id_2 == good1['id']))
                    ).first()
                    
                    if existing:
                        existing.similarity_score = similarity_score
                    else:
                        db.add(ProductSimilarity(
                            good_id_1=good1['id'],
                            good_id_2=good2['id'],
                            similarity_score=similarity_score
                        ))
        
        db.commit()
        logger.info(f"Updated product similarities for {len(goods)} products")
        
    except Exception as e:
        db.rollback()
        logger.error(f"Error updating product similarities: {str(e)}")