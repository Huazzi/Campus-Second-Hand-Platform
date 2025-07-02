import time
import schedule
import threading
import logging
from app.database import SessionLocal
from app.services.content_based import update_product_similarities
from dotenv import load_dotenv

# 加载环境变量
load_dotenv()

# 配置日志
logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s - %(name)s - %(levelname)s - %(message)s"
)
logger = logging.getLogger(__name__)

def update_similarities_job():
    """定时更新商品相似度表"""
    logger.info("开始更新商品相似度...")
    db = SessionLocal()
    try:
        update_product_similarities(db)
        logger.info("商品相似度更新完成")
    except Exception as e:
        logger.error(f"更新商品相似度出错: {str(e)}")
    finally:
        db.close()

def run_scheduler():
    """运行调度器"""
    # 每天凌晨3点更新商品相似度
    schedule.every().day.at("03:00").do(update_similarities_job)
    
    # 首次运行时立即更新一次
    update_similarities_job()
    
    while True:
        schedule.run_pending()
        time.sleep(60)  # 每分钟检查一次待执行的任务

if __name__ == "__main__":
    # 在后台线程中运行调度器
    scheduler_thread = threading.Thread(target=run_scheduler)
    scheduler_thread.daemon = True
    scheduler_thread.start()
    
    logger.info("调度器已启动")
    
    # 保持主线程运行
    try:
        while True:
            time.sleep(100)
    except (KeyboardInterrupt, SystemExit):
        logger.info("调度器已停止")