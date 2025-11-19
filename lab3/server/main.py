import uvicorn
from Dog import Dog as dog_data_class
from crud import get_dog_by_id, add_dog
from database import init_db
from fastapi import FastAPI, Form
from fastapi.middleware.cors import CORSMiddleware
app = FastAPI()


@app.get("/")
async def index():
    return {"message": "Hello World"}


@app.post("/postworld")
async def post_world():
    return {"message": "Hello World"}

@app.get("/calc")
async def calc(a: int, b: int):
    return {"result": a + b}

@app.post("/add_dog")
async def add_dog(
    name: str = Form(...),
    breed: str = Form(...)
):
    add_dog(name=name, breed=breed)
    return {"message": "Dog created"}

@app.get("/get_dog")
async def get_dog(dog_id):
    dog: dog_data_class = get_dog_by_id(dog_id)
    return {"id": dog.id, "name": dog.name, "breed": dog.breed}


if __name__ == "__main__":
    init_db()
    uvicorn.run(app, host="0.0.0.0", port=8000)

