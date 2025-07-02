 #!/bin/bash
# 推荐系统启动脚本

# 激活虚拟环境（如果有的话）
# source venv/bin/activate

# 安装依赖
echo "正在安装依赖..."
pip install -r requirements.txt

# 设置Python路径
export PYTHONPATH=$PYTHONPATH:$(pwd)

# 启动推荐服务
echo "正在启动推荐服务..."
uvicorn app.main:app --host 0.0.0.0 --port 8000 --reload

# 如需后台运行，使用以下命令替代上面的启动命令
# nohup uvicorn app.main:app --host 0.0.0.0 --port 8000 > recommendation_service.log 2>&1 &
# echo "推荐服务已在后台启动，日志写入到 recommendation_service.log"