package me.todoReminder.bot.commands.dev;

import me.todoReminder.bot.Bot;
import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandContext;

public class Shutdown extends Command {
    private static String name = "shutdown", description ="Shuts down the bot", usage;
    private static boolean requiresArgs = false;
    private static String[] aliases;

    public Shutdown() {
        super(name, description, usage, requiresArgs, aliases);
    }

    public void run(CommandContext ctx) {
        ctx.getTextChannel().sendMessage("Shutting down...").queue();
        Bot.shutdown(ctx.getJda(), "Bot shut down via the 'shutdown' command", null);
    }
}
