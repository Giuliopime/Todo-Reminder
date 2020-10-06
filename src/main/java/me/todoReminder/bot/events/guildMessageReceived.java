package me.todoReminder.bot.events;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class guildMessageReceived {
    private TextChannel channel;
    private Message message;

    public guildMessageReceived(GuildMessageReceivedEvent event) {
        message = event.getMessage();
        channel = event.getChannel();

    }
}
