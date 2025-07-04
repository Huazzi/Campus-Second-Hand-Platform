cd ai_chat_service
echo 正在安装依赖...
pip install -r requirements.txt
uvicorn app.main:app --host 0.0.0.0 --port 8001 --reload