from typing import List, Optional
from datetime import datetime
from pydantic import BaseModel

# 用户相关模型
class UserBase(BaseModel):
    id: int
    username: Optional[str]
    nickname: Optional[str]

    class Config:
        orm_mode = True

# 商品相关模型
class GoodBase(BaseModel):
    id: int
    goodname: str
    price: float
    description: str
    category_id: Optional[int]
    view_count: int
    inquiry_count: int
    location: Optional[str]
    time: datetime

    class Config:
        orm_mode = True

class GoodDetail(GoodBase):
    goodownerid: int
    num: int
    status: int
    isbn: Optional[str]
    chubanshe: Optional[str]
    author: Optional[str]
    chubantime: Optional[str]
    image: str

# 类别模型
class CategoryBase(BaseModel):
    id: int
    name: str
    parent_id: int

    class Config:
        orm_mode = True

# 用户视图模型
class UserViewCreate(BaseModel):
    user_id: int
    good_id: int
    view_duration: Optional[int] = 0

class UserViewResponse(UserViewCreate):
    id: int
    view_time: datetime

    class Config:
        orm_mode = True

# 用户偏好模型
class UserPreferenceCreate(BaseModel):
    user_id: int
    category_id: Optional[int] = None
    price_min: Optional[float] = None
    price_max: Optional[float] = None
    preferred_location: Optional[str] = None

class UserPreferenceResponse(UserPreferenceCreate):
    id: int
    last_updated: datetime

    class Config:
        orm_mode = True

# 推荐商品响应模型
class RecommendedGood(BaseModel):
    id: int
    goodname: str
    price: float
    description: str
    image: str
    view_count: int
    category_id: Optional[int]
    similarity_score: Optional[float] = None
    
    class Config:
        orm_mode = True

# 推荐请求模型
class RecommendationRequest(BaseModel):
    user_id: int
    limit: int = 10
    offset: int = 0

# 记录推荐点击模型
class RecommendationClickRequest(BaseModel):
    user_id: int
    recommended_good_id: int
    recommendation_type: str