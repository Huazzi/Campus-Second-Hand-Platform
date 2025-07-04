from fastapi import APIRouter, HTTPException
from app.schemas.chat import ChatRequest, ChatResponse
from app.services import ai_service

router = APIRouter()

@router.post("/chat", response_model=ChatResponse)
def get_chat_response(request: ChatRequest):
    """
    接收用户问题并返回AI生成的回答
    """
    if not request.question:
        raise HTTPException(status_code=400, detail="问题不能为空")
    
    try:
        answer = ai_service.get_answer(request.question)
        return ChatResponse(answer=answer)
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"AI服务内部错误: {e}")