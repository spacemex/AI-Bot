package com.github.spacemex.api;

import java.util.List;

public class OpenAIResponse {
    public String id;
    public String object;
    public long created;
    public String model;
    public List<Choice> choices;
    public Usage usage;

    public static class Choice {
        public int index;
        public Message message;
        public Object logprobs;
        public String finish_reason;
    }

    public static class Message {
        public String role;
        public String content;
    }

    public static class Usage {
        public int prompt_tokens;
        public int completion_tokens;
        public int total_tokens;
        public PromptTokensDetails prompt_tokens_details;
        public CompletionTokensDetails completion_tokens_details;
    }

    public static class PromptTokensDetails {
        public int cached_tokens;
    }

    public static class CompletionTokensDetails {
        public int reasoning_tokens;
    }
}