from sqlalchemy import Column, Integer, String, Float, ForeignKey, DateTime, Boolean, DECIMAL
from sqlalchemy.orm import relationship
from sqlalchemy.sql import func
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

# 映射现有数据库表的模型
class User(Base):
    __tablename__ = "users"
    
    id = Column(Integer, primary_key=True, autoincrement=True)
    username = Column(String(8))
    nickname = Column(String(20))
    head = Column(String(255), nullable=False)
    mobilephone = Column(String(11))
    address = Column(String(100))
    sex = Column(Integer, nullable=False)  # 1男 0女
    college = Column(String(255), nullable=False)
    openid = Column(String(255), nullable=False)
    
    # 关联关系
    views = relationship("UserView", back_populates="user")
    preferences = relationship("UserPreference", back_populates="user", uselist=False)
    recommendations = relationship("RecommendationLog", back_populates="user")

class Good(Base):
    __tablename__ = "goods"
    
    id = Column(Integer, primary_key=True, autoincrement=True)
    goodname = Column(String(255), nullable=False)
    goodownerid = Column(Integer, ForeignKey("users.id"), nullable=False)
    price = Column(DECIMAL(10, 2), nullable=False)
    description = Column(String(255), nullable=False)
    image = Column(String(255), nullable=False)
    num = Column(Integer, nullable=False)
    category_id = Column(Integer, ForeignKey("categories.id"))
    time = Column(DateTime, nullable=False)
    lid = Column(Integer)
    userId = Column(Integer)
    status = Column(Integer, default=0)
    view_count = Column(Integer, nullable=False, default=0)
    inquiry_count = Column(Integer, nullable=False, default=0)
    location = Column(String(100))
    isbn = Column(String(20))
    chubanshe = Column(String(100))
    author = Column(String(100))
    chubantime = Column(String(20))
    
    # 关联关系
    owner = relationship("User", foreign_keys=[goodownerid])
    category = relationship("Category")
    views = relationship("UserView", back_populates="good")
    recommendations = relationship("RecommendationLog", back_populates="good")

class Category(Base):
    __tablename__ = "categories"
    
    id = Column(Integer, primary_key=True, autoincrement=True)
    name = Column(String(50), nullable=False)
    parent_id = Column(Integer, default=0)
    
    # 关联关系
    goods = relationship("Good", back_populates="category")

class Collect(Base):
    __tablename__ = "collect"
    
    userId = Column(Integer, ForeignKey("users.id"), primary_key=True)
    goodId = Column(Integer, ForeignKey("goods.id"), primary_key=True)
    
    # 关联关系
    user = relationship("User")
    good = relationship("Good")

# 推荐系统的新表
class UserView(Base):
    __tablename__ = "user_views"
    
    id = Column(Integer, primary_key=True, autoincrement=True)
    user_id = Column(Integer, ForeignKey("users.id"), nullable=False)
    good_id = Column(Integer, ForeignKey("goods.id"), nullable=False)
    view_time = Column(DateTime, default=func.now(), onupdate=func.now(), nullable=False)
    view_duration = Column(Integer, default=0)
    
    # 关联关系
    user = relationship("User", back_populates="views")
    good = relationship("Good", back_populates="views")

class UserPreference(Base):
    __tablename__ = "user_preferences"
    
    id = Column(Integer, primary_key=True, autoincrement=True)
    user_id = Column(Integer, ForeignKey("users.id"), nullable=False, unique=True)
    category_id = Column(Integer, ForeignKey("categories.id"))
    price_min = Column(DECIMAL(10, 2))
    price_max = Column(DECIMAL(10, 2))
    preferred_location = Column(String(100))
    last_updated = Column(DateTime, default=func.now(), onupdate=func.now(), nullable=False)
    
    # 关联关系
    user = relationship("User", back_populates="preferences")
    category = relationship("Category")

class ProductSimilarity(Base):
    __tablename__ = "product_similarity"
    
    id = Column(Integer, primary_key=True, autoincrement=True)
    good_id_1 = Column(Integer, ForeignKey("goods.id"), nullable=False)
    good_id_2 = Column(Integer, ForeignKey("goods.id"), nullable=False)
    similarity_score = Column(Float, nullable=False, default=0)
    created_at = Column(DateTime, default=func.now(), nullable=False)
    updated_at = Column(DateTime, default=func.now(), onupdate=func.now(), nullable=False)
    
    # 关联关系
    good1 = relationship("Good", foreign_keys=[good_id_1])
    good2 = relationship("Good", foreign_keys=[good_id_2])

class RecommendationLog(Base):
    __tablename__ = "recommendation_logs"
    
    id = Column(Integer, primary_key=True, autoincrement=True)
    user_id = Column(Integer, ForeignKey("users.id"), nullable=False)
    recommended_good_id = Column(Integer, ForeignKey("goods.id"), nullable=False)
    recommended_time = Column(DateTime, default=func.now(), nullable=False)
    clicked = Column(Boolean, default=False, nullable=False)
    recommendation_type = Column(String(20), nullable=False)
    
    # 关联关系
    user = relationship("User", back_populates="recommendations")
    good = relationship("Good", back_populates="recommendations")