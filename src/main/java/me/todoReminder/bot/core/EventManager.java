package me.todoReminder.bot.core;

import me.todoReminder.bot.events.GuildMessageReceived;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventManager extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventManager.class);

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        LOGGER.info("ToDo & Reminder launched!");
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        GuildMessageReceived.execute(event);
    }
}
