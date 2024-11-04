package com.github.spacemex.api;

import com.github.spacemex.util.ENVConfig;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OpenAIClient {

    private final String apiKey;
    private final OkHttpClient client;
    private final Gson gson;

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final int MAX_TOKENS_PER_MINUTE = 40000;

    private int tokensUsedThisMinute = 0;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public OpenAIClient(ENVConfig env) {
        this.apiKey = env.Env().get("OPENAI_API_KEY");
        this.client = new OkHttpClient();
        this.gson = new Gson();

        // Reset token usage every minute
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Resetting tokens used this minute.");
            tokensUsedThisMinute = 0;
        }, 0, 1, TimeUnit.MINUTES);
    }

    public String sendChatCompletionRequest(String prompt) throws IOException {
        int tokensRequired = countTokens(prompt);

        if (tokensUsedThisMinute + tokensRequired > MAX_TOKENS_PER_MINUTE) {
            throw new IOException("Token limit exceeded. Please wait before sending more requests. Tokens used: " + tokensUsedThisMinute + ", Tokens needed: " + tokensRequired);
        }

        tokensUsedThisMinute += tokensRequired;
        return executeRequest(prompt);
    }

    private String executeRequest(String prompt) throws IOException {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", List.of(Map.of("role", "user", "content", prompt)));

        Request request = new Request.Builder()
                .url(API_URL)
                .post(RequestBody.create(gson.toJson(requestBody), MediaType.get("application/json; charset=utf-8")))
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        int retryCount = 0;
        int maxRetries = 5;
        long waitTime = 1000;  // Initial wait time in milliseconds (1 second)

        while (retryCount < maxRetries) {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    OpenAIResponse openAIResponse = gson.fromJson(responseBody, OpenAIResponse.class);
                    if (!openAIResponse.choices.isEmpty()) {
                        return openAIResponse.choices.get(0).message.content;
                    } else {
                        throw new IOException("No choices found in the response");
                    }
                } else if (response.code() == 429) {
                    // Handle rate limit
                    String retryAfter = response.header("Retry-After");
                    System.out.println("Rate Limited");
                    if (retryAfter != null) {
                        waitTime = Long.parseLong(retryAfter) * 1000;
                    }
                } else {
                    throw new IOException("Unexpected response code: " + response.code());
                }
            }
            // Wait before retrying
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IOException("Interrupted during wait", e);
            }
            retryCount++;
            // Exponential backoff
            waitTime *= 2;
        }

        throw new IOException("Failed to get a successful response after " + maxRetries + " retries");
    }

    private int countTokens(String text) {
        // Simplistic token count estimation (tokens are roughly equal to words)
        // In reality, you should use OpenAI's tokenizer for accurate token counts.
        int tokenCount = text.split("\\s+").length;
        System.out.println("Token count for prompt: " + tokenCount);
        return tokenCount;
    }
}