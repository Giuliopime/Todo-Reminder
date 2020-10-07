package me.todoReminder.bot.events;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class guildMessageReceived {
    private final TextChannel channel;
    private final Message message;

    public guildMessageReceived(GuildMessageReceivedEvent event) {
        message = event.getMessage();
        channel = event.getChannel();

        if(message.getContentRaw().startsWith("t!")) {

        }
    }
}
