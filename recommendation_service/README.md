 # 智能推荐服务

本服务为校园二手交易平台提供智能推荐功能，包括基于内容的推荐、热门商品推荐和新品推荐。

## 功能特点

- 基于内容的推荐：根据用户历史浏览、收藏行为推荐相似商品
- 热门商品推荐：基于商品浏览量、收藏量等热度指标进行推荐
- 新品推荐：推荐最近上架的新商品
- 用户行为记录：记录用户浏览、点击行为，用于优化推荐算法
- 定时任务：自动更新商品相似度矩阵

## 技术栈

- FastAPI: 现代高性能Web框架
- SQLAlchemy: ORM数据库访问框架
- Pandas/NumPy: 数据处理与分析
- Schedule: 定时任务调度器

### 标准框架
```text
.
├── app/
│   ├── __init__.py
│   ├── main.py              # 启动入口
│   ├── api/                 # 路由分发
│   │   ├── __init__.py
│   │   └── v1/
│   │       ├── __init__.py
│   │       └── endpoints.py
│   ├── models/              # 数据模型（ORM、Pydantic）
│   │   └── user.py
│   ├── schemas/             # Pydantic 数据校验模型
│   │   └── user.py
│   ├── services/            # 业务逻辑
│   │   └── user_service.py
│   ├── db/                  # 数据库连接与会话管理
│   │   ├── base.py
│   │   └── session.py
│   └── core/                # 配置、初始化等
│       └── config.py
├── requirements.txt         # 依赖文件
├── .env                     # 环境变量

```

## 安装与启动

### 环境要求

- Python 3.8+
- MySQL 5.7+

### 安装依赖

```bash
pip install -r requirements.txt
```

### 配置环境变量

创建`.env`文件，配置以下参数：

```
# 数据库配置
DB_HOST=localhost
DB_PORT=3306
DB_NAME=second_hand
DB_USER=root
DB_PASS=123456

# API服务配置
API_HOST=0.0.0.0
API_PORT=8000
LOG_LEVEL=INFO
```

### 启动服务

方法1：直接启动
```bash
# 设置Python路径
export PYTHONPATH=$PYTHONPATH:$(pwd)

# 启动API服务
uvicorn app.main:app --host 0.0.0.0 --port 8000 --reload
```

方法2：使用启动脚本
```bash
chmod +x start.sh
./start.sh
```

方法3：使用Docker Compose
```bash
docker-compose up -d
```

## API接口文档

启动服务后，访问以下地址查看完整API文档：
- http://localhost:8000/docs (Swagger UI)
- http://localhost:8000/redoc (ReDoc UI)

### 主要接口

- `POST /recommendations/content-based`: 基于内容的推荐
- `POST /recommendations/popular`: 热门商品推荐
- `POST /recommendations/new-arrivals`: 新上架商品推荐
- `POST /user-views`: 记录用户浏览行为
- `POST /recommendations/click`: 记录推荐点击事件

## 微信小程序集成

在微信小程序中，通过以下方式调用推荐API：

```javascript
// 基于内容的个性化推荐
wx.request({
  url: `${baseUrl}/api/recommendations/content-based`,
  method: 'POST',
  data: {
    user_id: userId,
    limit: 10,
    offset: 0
  },
  success: (res) => {
    // 处理推荐结果
  }
});
```