package com.github.spacemex.util;

import com.github.spacemex.api.OpenAIClient;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandManager extends ListenerAdapter {

    private final List<CommandData> commandData = new ArrayList<>();
    private final OpenAIClient aiClient;

    public CommandManager(OpenAIClient aiClient) {
        this.aiClient = aiClient;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String cmd = event.getName();
        String User = event.getUser().getAsMention();

        if (cmd.equals("ask")) {
            event.deferReply().queue();
            String question = Objects.requireNonNull(event.getOption("question")).getAsString();

            event.getHook().sendMessage("Please wait while I fetch the response...").queue();

            new Thread(() -> {
                try {
                    String response = aiClient.sendChatCompletionRequest(question);
                    event.getHook().sendMessage(User + " " + response).queue();
                } catch (IOException e) {
                    event.getHook().sendMessage("Error fetching response from OpenAI: " + e.getMessage()).queue();
                }
            }).start();
        }
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        commandData.add(Commands.slash("ask", "Ask AI-Bot a question.")
                .addOption(OptionType.STRING, "question", "Question to ask", true));
        event.getJDA().updateCommands().addCommands(commandData).queue();
    }
}