from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
from transformers import pipeline

# Create FastAPI app
app = FastAPI()

# Enable CORS so browser frontend can connect
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],   # allow all origins for now
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Load sentiment analysis model
sentiment_analyzer = pipeline("sentiment-analysis")

# Define request schema
class TextPayload(BaseModel):
    text: str

# Helper functions for descriptive output
def detect_context(text: str) -> str:
    t = text.lower()
    if "movie" in t: return "a movie"
    if "product" in t: return "a product"
    if "food" in t: return "food"
    return "a general topic"

def map_emotion(label: str) -> str:
    if label == "POSITIVE": return "feels good"
    if label == "NEGATIVE": return "feels bad"
    if label == "NEUTRAL": return "feels indifferent"
    return "has an unknown feeling"

# Endpoint
@app.post("/analyze")
def analyze_text(payload: TextPayload):
    result = sentiment_analyzer(payload.text)[0]
    label = result["label"]
    context = detect_context(payload.text)
    emotion = map_emotion(label)

    description = (
        f"The given sentence is a {label.lower()} statement "
        f"about {context}, and the user {emotion}."
    )

    return {"description": description}

# Optional health check
@app.get("/health")
def health_check():
    return {"status": "ok"}