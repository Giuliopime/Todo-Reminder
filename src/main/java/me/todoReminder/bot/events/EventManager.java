package me.todoReminder.bot.events;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventManager extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventManager.class);
    private static EventWaiter waiter;

    public EventManager(EventWaiter waiter) {
        EventManager.waiter = waiter;
    }

    public static EventWaiter getWaiter() {
        return waiter;
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        LOGGER.info("Todo & Reminder bot launched!");
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        MessageReceived.execute(event);
    }
}
