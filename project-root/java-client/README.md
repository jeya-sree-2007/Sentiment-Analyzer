# Java Client for FastAPI Sentiment Analyzer

## 📖 Overview
This project is a **Java client** that connects to a **FastAPI ML service** for sentiment analysis.  
It sends user input sentences to the FastAPI backend and prints descriptive results such as:

> "The given sentence is a positive statement about 'This movie was amazing!', and the user feels good. (confidence: 0.9999)"

---

## ⚙️ Features
- Interactive Java client (type sentences in terminal).
- Connects to FastAPI ML service via HTTP (OkHttp).
- Parses JSON responses using Gson.
- Produces **context-aware descriptive outputs** (positive, negative, neutral).
- Clean Maven setup with dependencies managed in `pom.xml`.

---

## 🚀 How to Run
1. Start the FastAPI service:
   ```bash
   uvicorn main:app --reload
## 🔄 How It Works
1. The user types a sentence in the Java client.
2. The client sends the sentence as JSON to FastAPI (`/analyze`).
3. FastAPI passes the text to the ML model.
4. The ML model predicts sentiment (positive/negative/neutral) with confidence.
5. FastAPI returns JSON response.
6. Java client parses the response and prints a descriptive sentence.