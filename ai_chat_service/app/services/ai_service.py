import os
import requests
from dotenv import load_dotenv

load_dotenv()

# 从环境变量中获取API密钥和模型配置
API_KEY = os.getenv("XF_API_KEY", "QVnxGEpbsJJATUrIwFlg:HjRIUlMNRjJlcsQhOioh")
MODEL = os.getenv("XF_MODEL", "4.0Ultra")
AI_API_URL = "https://spark-api-open.xf-yun.com/v1/chat/completions"

PREDEFINED_ANSWERS = {
    "如何发布二手物品": "发布二手物品很简单，只需点击小程序底部的「发布」按钮，填写物品名称、价格、描述和上传图片等信息，然后点击发布即可。",
    "如何联系卖家": "您可以在商品详情页点击「联系卖家」按钮，进入聊天界面与卖家沟通。",
    "如何修改个人信息": "进入「我的」页面，点击头像或昵称，即可进入个人信息页面进行修改。",
    "如何查看我的订单": "在「我的」页面中，您可以点击「我的订单」查看所有购买记录。",
    "如何删除已发布的商品": "在「我的」页面中，点击「我发布的」，找到要删除的商品，长按或点击右上角的「···」按钮，选择删除即可。",
    "如何退款": "目前平台支持线下交易，如需退款请直接与卖家协商。如有纠纷，可以联系平台客服处理。",
    "忘记密码怎么办": "您可以在登录页面点击「忘记密码」，通过绑定的手机号或邮箱进行密码重置。",
    "如何加入购物车": "浏览商品时，点击商品详情页的「加入购物车」按钮即可。",
    "如何搜索商品": "在首页顶部的搜索框中输入关键词，点击搜索即可查找相关商品。",
    "平台收费标准": "我们的平台目前不收取任何手续费，交易完全免费。"
}

def get_answer(question: str) -> str:
    """
    根据用户问题获取AI回答
    """
    # 首先检查预定义回答
    for key, value in PREDEFINED_ANSWERS.items():
        if key in question:
            return value

    try:
        return call_spark_api(question)
    except Exception as e:
        print(f"讯飞星火API调用失败: {e}")
        return "很抱歉，我现在无法回答这个问题。请尝试询问关于发布商品、购买流程、个人信息修改等问题，或联系人工客服。"

def call_spark_api(question: str) -> str:
    """
    调用讯飞星火API
    """
    headers = {
        "Content-Type": "application/json",
        "Authorization": f"Bearer {API_KEY}"
    }

    payload = {
        "model": MODEL,
        "messages": [
            {
                "role": "system",
                "content": "你是校园二手交易平台「二货来了」的智能客服助手，名叫小二。你的任务是帮助用户解答关于平台使用、商品发布、交易流程等问题。请使用礼貌友好的语气，回答要简洁明了。输出回答内容禁止使用Markdown语法。"
            },
            {
                "role": "user",
                "content": question
            }
        ],
        "temperature": 0.7,
        "max_tokens": 4096,
        "stream": False
    }

    response = requests.post(AI_API_URL, json=payload, headers=headers)
    response.raise_for_status()  # 如果请求失败则抛出HTTPError

    response_data = response.json()

    if "code" in response_data and response_data["code"] != 0:
        error_msg = f"AI服务出错，错误码: {response_data['code']}"
        if "message" in response_data:
            error_msg += f", 错误信息: {response_data['message']}"
        print(error_msg)
        return "抱歉，AI服务暂时不可用，请稍后再试。"

    if "choices" in response_data and response_data["choices"]:
        choice = response_data["choices"][0]
        if "message" in choice and "content" in choice["message"]:
            return choice["message"]["content"]

    return "非常感谢您的问题，我是智能客服小二，很高兴为您服务。请问还有什么可以帮助您的吗？"