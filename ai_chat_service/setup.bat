echo 正在安装依赖...
pip install -r requirements.txt
cd ai_chat_service
uvicorn app.main:app --host 0.0.0.0 --port 8001 --reload