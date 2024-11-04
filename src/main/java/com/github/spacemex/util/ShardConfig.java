package com.github.spacemex.util;

import com.github.spacemex.api.OpenAIClient;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

public class ShardConfig {

    private final ShardManager shardManager;

    public ShardConfig(ENVConfig env, OpenAIClient ai) {

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(env.Env().get("TOKEN"));
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.playing("Player's Yapping"));

        shardManager = builder.build();

        shardManager.addEventListener(new CommandManager(ai));
    }

    public ShardManager getManager(){
        return shardManager;
    }
}
