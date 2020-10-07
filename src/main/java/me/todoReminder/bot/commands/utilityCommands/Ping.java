package me.todoReminder.bot.commands.utilityCommands;

import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandContext;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Ping extends Command {
    private static final String name = "ping", description = "Check the bot latency", usage = null;
    private static final boolean requiresArgs = false;
    private static final String[] aliases = null;

    public Ping() {
        super(name, description, usage, requiresArgs, aliases);
    }

    public void run(CommandContext ctx) {
        ctx.getEvent().getMessage().getChannel().sendMessage("pong!").queue();
    }
}