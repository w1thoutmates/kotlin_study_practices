from sqlalchemy.orm import Session
from Dog import Dog
from database import SessionLocal

def add_dog(name: str, breed: str):
    session = SessionLocal()
    try:
        new_dog = Dog(name=name, breed=breed)
        session.add(new_dog)
        session.commit()
        session.refresh(new_dog)
        return new_dog
    except Exception as e:
        session.rollback()
        raise e
    finally:
        session.close()

def get_dog_by_id(dog_id: int):
    db = SessionLocal()
    try:
        dog = db.query(Dog).filter(Dog.id == dog_id).first()
        return dog
    finally:
        db.close()