package me.todoReminder.bot.core.commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.bson.Document;

public class CommandContext {
    private final MessageReceivedEvent event;
    private final String commandName;
    private final String args;
    private final JDA jda;
    private final Document userData;
    private final MessageChannel channel;


    public CommandContext(MessageReceivedEvent event, String commandName, String args, Document userData) {
        this.event = event;
        this.commandName = commandName;
        this.args = args;
        this.userData = userData;
        jda = event.getJDA();
        channel = event.getChannel();
    }

    // Get full events
    public MessageReceivedEvent getEvent() {
        return event;
    }

    public void sendMessage(Message m) {
        channel.sendMessage(m).queue();
    }

    public void sendMessage(String m) {
        channel.sendMessage(m).queue();
    }

    public void sendMessage(MessageEmbed m) {
        channel.sendMessage(m).queue();
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public User getUser() {
        return event.getAuthor();
    }

    public Message getMessage() {
        return event.getMessage();
    }

    // Database
    public Document getUserData() {
        return userData;
    }

    public String getPrefix() {
        return userData.get("prefix").toString();
    }


    public String getCommandName() {
        return commandName;
    }

    public String getArgs() {
        return args;
    }

    public JDA getJda() {
        return jda;
    }
}
