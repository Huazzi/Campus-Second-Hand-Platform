from fastapi import FastAPI
from starlette.middleware.cors import CORSMiddleware

from app.api.v1 import endpoints

app = FastAPI(
    title="二货来了 AI Chat Service",
    description="AI智能客服功能模块",
    version="1.0.0"
)

# 配置CORS
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # 允许所有来源，生产环境中应配置为特定来源
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

app.include_router(endpoints.router, prefix="/api/v1")

@app.get("/")
def read_root():
    return {"message": "欢迎来到二货来了的 AI Chat Service"}
