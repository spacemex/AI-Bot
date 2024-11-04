package com.github.spacemex.aibot;

import com.github.spacemex.api.OpenAIClient;
import com.github.spacemex.util.ENVConfig;
import com.github.spacemex.util.ShardConfig;

public class AIBot {

    public static void main(String[] args) {
        try {
            ENVConfig env = new ENVConfig();
            OpenAIClient ai = new OpenAIClient(env);
            ShardConfig shardConfig = new ShardConfig(env, ai);
        }catch (Exception e){
            System.out.println("Invalid token: " + e.getMessage());
        }
    }
}
