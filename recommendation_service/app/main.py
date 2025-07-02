from fastapi import FastAPI, Depends, HTTPException, Query
from fastapi.middleware.cors import CORSMiddleware
from sqlalchemy.orm import Session
from typing import List, Optional
import logging
import os
from dotenv import load_dotenv
import threading    # 导入调度器
from app.services.content_based import update_product_similarities

from app.database import get_db
from app.models import UserView, RecommendationLog
from app.schemas import (
    RecommendedGood,
    RecommendationRequest,
    UserViewCreate,
    RecommendationClickRequest
)
from app.services.content_based import get_content_based_recommendations
from app.services.popularity import get_popular_recommendations, get_new_arrivals

# 加载环境变量
load_dotenv()

# 配置日志
logging.basicConfig(
    level=os.getenv("LOG_LEVEL", "INFO"),
    format="%(asctime)s - %(name)s - %(levelname)s - %(message)s"
)
logger = logging.getLogger(__name__)

# 创建FastAPI应用
app = FastAPI(
    title="商品推荐API",
    description="【二货来了】平台智能推荐系统API",
    version="0.1.0"
)

# 添加CORS中间件
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.get("/")
def read_root():
    return {"message": "推荐系统API成功启动！"}

@app.get("/recommendations/content-based", response_model=List[RecommendedGood])
def recommend_content_based(
    user_id: int,
    limit: int = 10,
    offset: int = 0,
    db: Session = Depends(get_db)
):
    """基于内容的推荐接口"""
    recommendations = get_content_based_recommendations(
        db=db,
        user_id=user_id,
        limit=limit,
        offset=offset
    )
    
    # 记录推荐日志
    for rec in recommendations:
        log = RecommendationLog(
            user_id=user_id,
            recommended_good_id=rec.id,
            recommendation_type="content-based"
        )
        db.add(log)
    
    try:
        db.commit()
    except Exception as e:
        db.rollback()
        logger.error(f"Error logging recommendations: {str(e)}")
    
    return recommendations

@app.get("/recommendations/popular", response_model=List[RecommendedGood])
def recommend_popular(
    limit: int = 10,
    offset: int = 0,
    category_id: Optional[int] = None,
    user_id: Optional[int] = None,
    db: Session = Depends(get_db)
):
    """热门商品推荐接口"""
    recommendations = get_popular_recommendations(
        db=db,
        user_id=user_id,
        category_id=category_id,
        limit=limit,
        offset=offset
    )
    
    # 如果指定了用户ID，记录推荐日志
    if user_id:
        for rec in recommendations:
            log = RecommendationLog(
                user_id=user_id,
                recommended_good_id=rec.id,
                recommendation_type="popular"
            )
            db.add(log)
        
        try:
            db.commit()
        except Exception as e:
            db.rollback()
            logger.error(f"Error logging recommendations: {str(e)}")
    
    return recommendations

@app.get("/recommendations/new-arrivals", response_model=List[RecommendedGood])
def recommend_new_arrivals(
    days: int = 7,
    limit: int = 10,
    offset: int = 0,
    user_id: Optional[int] = None,
    db: Session = Depends(get_db)
):
    """新上架商品推荐接口"""
    recommendations = get_new_arrivals(
        db=db,
        days=days,
        limit=limit,
        offset=offset
    )
    
    # 如果指定了用户ID，记录推荐日志
    if user_id:
        for rec in recommendations:
            log = RecommendationLog(
                user_id=user_id,
                recommended_good_id=rec.id,
                recommendation_type="new-arrivals"
            )
            db.add(log)
        
        try:
            db.commit()
        except Exception as e:
            db.rollback()
            logger.error(f"Error logging recommendations: {str(e)}")
    
    return recommendations

@app.post("/user-views", status_code=201)
def record_user_view(
    view: UserViewCreate,
    db: Session = Depends(get_db)
):
    """记录用户浏览行为"""
    try:
        # 检查是否已存在相同记录
        existing = db.query(UserView).filter(
            UserView.user_id == view.user_id,
            UserView.good_id == view.good_id
        ).first()
        
        if existing:
            # 更新现有记录
            existing.view_duration += view.view_duration
            db.commit()
        else:
            # 创建新记录
            db_view = UserView(**view.dict())
            db.add(db_view)
            db.commit()
            
        return {"status": "success"}
    except Exception as e:
        db.rollback()
        logger.error(f"Error recording user view: {str(e)}")
        raise HTTPException(status_code=500, detail="Failed to record user view")

@app.post("/recommendations/click", status_code=200)
def record_recommendation_click(
    click: RecommendationClickRequest,
    db: Session = Depends(get_db)
):
    """记录推荐点击事件"""
    try:
        # 查找最近的推荐记录
        recent_rec = db.query(RecommendationLog).filter(
            RecommendationLog.user_id == click.user_id,
            RecommendationLog.recommended_good_id == click.recommended_good_id,
            RecommendationLog.recommendation_type == click.recommendation_type,
            RecommendationLog.clicked == False
        ).order_by(RecommendationLog.recommended_time.desc()).first()
        
        if recent_rec:
            # 更新点击状态
            recent_rec.clicked = True
            db.commit()
        else:
            # 创建新点击记录
            new_rec = RecommendationLog(
                user_id=click.user_id,
                recommended_good_id=click.recommended_good_id,
                recommendation_type=click.recommendation_type,
                clicked=True
            )
            db.add(new_rec)
            db.commit()
        
        return {"status": "success"}
    except Exception as e:
        db.rollback()
        logger.error(f"Error recording recommendation click: {str(e)}")
        raise HTTPException(status_code=500, detail="Failed to record click")


def start_scheduler():
    """启动调度器"""
    try:
        from app.services.schedule_tasks import run_scheduler
        scheduler_thread = threading.Thread(target=run_scheduler)
        scheduler_thread.daemon = True
        scheduler_thread.start()
        logger.info("推荐系统调度器已启动")
    except Exception as e:
        logger.error(f"启动调度器失败: {str(e)}")

if __name__ == "__main__":
    import uvicorn
    
    # 启动调度器
    start_scheduler()
    
    # 启动API服务
    host = os.getenv("API_HOST", "0.0.0.0")
    port = int(os.getenv("API_PORT", 8000))
    
    uvicorn.run("app.main:app", host=host, port=port, reload=True)