package com.sentiment;

import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class App {
    public static void main(String[] args) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        Scanner scanner = new Scanner(System.in);

        System.out.println("👋 Hi there! Share your experience (type 'exit' to quit):");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            if (input.trim().isEmpty()) {
                System.out.println("⚠️ Please type a sentence before pressing Enter.");
                continue;
            }

            // Build JSON body
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("text", input);
            String json = gson.toJson(jsonObject);

            RequestBody body = RequestBody.create(
                json, MediaType.parse("application/json"));

            Request request = new Request.Builder()
                .url("http://127.0.0.1:8000/analyze")
                .post(body)
                .build();

            // ✅ response only exists inside this try block
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    System.out.println("Error: " + response);
                    continue;
                }

                // ✅ result declared only once
                JsonObject result = gson.fromJson(response.body().string(), JsonObject.class);

                if (result.has("label") && result.has("score")) {
                    String label = result.get("label").getAsString();
                    double score = result.get("score").getAsDouble();

                    String description = SentenceBuilder.buildDescription(input, label, score);
                    System.out.println(description);
                } else {
                    System.out.println("⚠️ Unexpected response: " + result.toString());
                }
            }
        }

        scanner.close();
    }
}