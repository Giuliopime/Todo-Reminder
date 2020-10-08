package me.todoReminder.bot.core.commands;

import com.mongodb.DBObject;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;

public class CommandContext {
    private final GuildMessageReceivedEvent guildEvent;
    private final MessageReceivedEvent dmEvent;
    private final String commandName;
    private final String[] args;
    private final JDA jda;
    private final DBObject userData;


    public CommandContext(GuildMessageReceivedEvent guildEvent, MessageReceivedEvent dmEvent, String commandName, String[] args, DBObject userData) {
        this.guildEvent = guildEvent;
        this.dmEvent = dmEvent;
        this.commandName = commandName;
        this.args = args;
        this.userData = userData;
        jda = guildEvent.getJDA();
    }

    public GuildMessageReceivedEvent getGuildEvent() {
        return guildEvent;
    }

    public MessageReceivedEvent getDMEvent() {
        return dmEvent;
    }

    public String getPrefix() {
        return userData.get("prefix").toString();
    }
    public String getCommandName() {
        return commandName;
    }

    public String[] getArgs() {
        return args;
    }

    public JDA getJda() {
        return jda;
    }

    public TextChannel getTextChannel() {
        return guildEvent.getChannel();
    }

    public Member getMember() {
        return guildEvent.getMember();
    }
}
