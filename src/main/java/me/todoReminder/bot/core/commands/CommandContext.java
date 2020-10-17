package me.todoReminder.bot.core.commands;

import me.todoReminder.bot.core.database.schemas.GuildSchema;
import me.todoReminder.bot.core.database.schemas.TodoList;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import me.todoReminder.bot.core.database.schemas.UserModel;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class CommandContext {
    private final JDA jda;
    private final MessageReceivedEvent event;
    private final MessageChannel channel;
    private String commandName;
    private String args;
    private final String prefix;
    private int listIndex;
    private final UserModel userData;


    public CommandContext(MessageReceivedEvent event, String commandName, String args, UserModel userData, int listIndex, String prefix) {
        jda = event.getJDA();
        this.event = event;
        channel = event.getChannel();
        this.commandName = commandName;
        this.args = args;
        this.prefix = prefix;
        this.listIndex = listIndex;
        this.userData = userData;
    }


    // Useful methods
    public void sendMessage(EmbedBuilder m) {
        channel.sendMessage(m.build()).queue();
    }

    public String getUserID() {
        return userData.getUserID();
    }

    // Database
    public List<TodoList> getTodoLists() {
        return userData.getTodoLists();
    }

    public TodoList getSelectedList() {
        return userData.getTodoLists().get(listIndex);
    }

    // Getters
    public JDA getJda() {
        return jda;
    }

    public MessageReceivedEvent getEvent() {
        return event;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getArgs() {
        return args;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getListIndex() {
        return listIndex;
    }


    // Setters
    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public void setListIndex(int listChoice) {
        this.listIndex = listChoice;
    }
}
