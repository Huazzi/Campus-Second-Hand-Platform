#!/usr/bin/env python3
"""
调试脚本：检查推荐系统是否能正确读取view_count数据
"""

import sys
import os
sys.path.append(os.path.dirname(os.path.abspath(__file__)))

from app.database import SessionLocal
from app.models import Good
from sqlalchemy import text

def test_view_count_data():
    """测试view_count数据读取"""
    db = SessionLocal()
    try:
        print("=== 推荐系统数据库连接测试 ===")
        
        # 1. 测试数据库连接
        result = db.execute(text("SELECT VERSION()"))
        version = result.fetchone()[0]
        print(f"✅ 数据库连接成功，版本: {version}")
        
        # 2. 检查goods表结构
        print("\n=== 检查goods表结构 ===")
        result = db.execute(text("DESCRIBE goods"))
        columns = result.fetchall()
        
        has_view_count = False
        has_collect_count = False
        
        for column in columns:
            column_name = column[0]
            if column_name == 'view_count':
                has_view_count = True
                print(f"✅ 找到view_count字段: {column}")
            elif column_name == 'collect_count':
                has_collect_count = True
                print(f"✅ 找到collect_count字段: {column}")
        
        if not has_view_count:
            print("❌ 缺少view_count字段")
        if not has_collect_count:
            print("❌ 缺少collect_count字段")
        
        # 3. 检查实际数据
        print("\n=== 检查实际数据 ===")
        result = db.execute(text("""
            SELECT id, goodname, view_count, collect_count, status, num
            FROM goods 
            WHERE status = 0 AND num > 0 
            ORDER BY view_count DESC 
            LIMIT 10
        """))
        
        goods_data = result.fetchall()
        print(f"找到 {len(goods_data)} 条有效商品数据:")
        
        for good in goods_data:
            print(f"  ID:{good[0]} 名称:{good[1]} 浏览:{good[2]} 收藏:{good[3]} 状态:{good[4]} 数量:{good[5]}")
        
        # 4. 使用ORM查询测试
        print("\n=== ORM查询测试 ===")
        orm_goods = db.query(Good).filter(
            Good.status == 0,
            Good.num > 0
        ).order_by(Good.view_count.desc()).limit(5).all()
        
        print(f"ORM查询到 {len(orm_goods)} 条商品:")
        for good in orm_goods:
            print(f"  ID:{good.id} 名称:{good.goodname} 浏览:{good.view_count} 收藏:{getattr(good, 'collect_count', 'N/A')}")
        
        # 5. 统计信息
        print("\n=== 统计信息 ===")
        stats = db.execute(text("""
            SELECT 
                COUNT(*) as total_goods,
                COUNT(CASE WHEN view_count > 0 THEN 1 END) as goods_with_views,
                MAX(view_count) as max_views,
                AVG(view_count) as avg_views
            FROM goods 
            WHERE status = 0 AND num > 0
        """)).fetchone()
        
        print(f"总商品数: {stats[0]}")
        print(f"有浏览记录的商品: {stats[1]}")
        print(f"最大浏览次数: {stats[2]}")
        print(f"平均浏览次数: {stats[3]:.2f}")
        
    except Exception as e:
        print(f"❌ 错误: {e}")
    finally:
        db.close()

if __name__ == "__main__":
    test_view_count_data()
