from sqlalchemy import Column, Integer, String
from database import Base

class Dog(Base):
    __tablename__ = "dog"
    id = Column(Integer, primary_key=True)
    name = Column(String, index=True)
    breed = Column(String, index=True)