from sqlalchemy.orm import Session
from sqlalchemy import func, desc
from typing import List, Optional
import logging
from datetime import datetime, timedelta

from app.models import Good, UserView, Collect
from app.schemas import RecommendedGood

logger = logging.getLogger(__name__)

def get_popular_recommendations(
    db: Session, 
    user_id: Optional[int] = None,
    category_id: Optional[int] = None,
    limit: int = 10, 
    offset: int = 0
) -> List[RecommendedGood]:
    """
    基于热门程度的商品推荐
    结合浏览量、收藏数、询问次数等
    """
    try:
        # 基础查询 - 有效商品
        query = db.query(Good).filter(Good.status == 0, Good.num > 0)
        
        # 如果指定了类别，添加类别筛选
        if category_id:
            query = query.filter(Good.category_id == category_id)
            
        # 计算热度分数 = 2*view_count + 3*inquiry_count
        # 使用COALESCE处理NULL值，确保计算正确
        heat_score = func.coalesce(Good.view_count, 0) * 2 + func.coalesce(Good.inquiry_count, 0) * 3
        query = query.order_by(desc(heat_score))
        
        # 获取热门商品
        popular_goods = query.offset(offset).limit(limit).all()
        
        # 转换为响应格式
        result = []
        for good in popular_goods:
            # 使用goods表的collect_count字段，避免实时COUNT查询
            collect_count = getattr(good, 'collect_count', 0) or 0

            # 调试日志：打印商品数据
            logger.info(f"商品数据 - ID:{good.id}, 名称:{good.goodname}, 浏览次数:{good.view_count}, 收藏次数:{collect_count}")

            result.append(
                RecommendedGood(
                    id=good.id,
                    goodname=good.goodname,
                    price=float(good.price),
                    description=good.description,
                    image=good.image,
                    view_count=good.view_count or 0,  # 确保不返回None
                    category_id=good.category_id,
                    collect=collect_count
                )
            )
        
        return result
        
    except Exception as e:
        logger.error(f"Popular recommendation error: {str(e)}")
        return []

def get_new_arrivals(
    db: Session,
    days: int = 7,
    limit: int = 10,
    offset: int = 0
) -> List[RecommendedGood]:
    """
    获取最新上架的商品
    """
    try:
        # 计算日期范围
        recent_date = datetime.now() - timedelta(days=days)
        
        # 查询最近上架的商品
        query = db.query(Good).filter(
            Good.status == 0, 
            Good.num > 0,
            Good.time >= recent_date
        ).order_by(desc(Good.time))
        
        # 获取新品
        new_goods = query.offset(offset).limit(limit).all()
        
        # 转换为响应格式
        result = []
        for good in new_goods:
            # 使用goods表的collect_count字段，避免实时COUNT查询
            collect_count = getattr(good, 'collect_count', 0) or 0

            # 调试日志：打印商品数据
            logger.info(f"新品数据 - ID:{good.id}, 名称:{good.goodname}, 浏览次数:{good.view_count}, 收藏次数:{collect_count}")

            result.append(
                RecommendedGood(
                    id=good.id,
                    goodname=good.goodname,
                    price=float(good.price),
                    description=good.description,
                    image=good.image,
                    view_count=good.view_count or 0,  # 确保不返回None
                    category_id=good.category_id,
                    collect=collect_count
                )
            )
        
        return result
        
    except Exception as e:
        logger.error(f"New arrivals recommendation error: {str(e)}")
        return []