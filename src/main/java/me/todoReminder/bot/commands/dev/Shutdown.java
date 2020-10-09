package me.todoReminder.bot.commands.dev;

import me.todoReminder.bot.Bot;
import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;

public class Shutdown extends Command {
    private static final String name = "shutdown", description ="Shuts down the bot", usage = null;
    private static final CommandCategory category = CommandCategory.DEVELOPER;
    private static final boolean requiresArgs = false;
    private static final String[] aliases = null;

    public Shutdown() {
        super(name, description, usage, category, requiresArgs, aliases);
    }

    public void run(CommandContext ctx) {
        ctx.sendMessage("Shutting down...");
        Bot.shutdown(ctx.getJda(), "Bot shut down via the 'shutdown' command", null);
    }
}
