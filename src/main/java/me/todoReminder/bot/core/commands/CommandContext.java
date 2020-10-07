package me.todoReminder.bot.core.commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.w3c.dom.Text;

import java.util.Arrays;

public class CommandContext {
    private final GuildMessageReceivedEvent event;
    private final String commandName;
    private final String[] args;
    private final JDA jda;


    public CommandContext(GuildMessageReceivedEvent event, String commandName, String message) {
        this.event = event;
        this.commandName = commandName;
        this.args = getCommandArgs(message);
        jda = event.getJDA();
    }

    private String[] getCommandArgs(String message) {
        String[] args = message.split("/ +/");
        return Arrays.copyOfRange(args, 1, args.length);
    }

    public GuildMessageReceivedEvent getEvent() {
        return event;
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
        return event.getChannel();
    }
}
