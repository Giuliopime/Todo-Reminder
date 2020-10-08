package me.todoReminder.bot.commands.utility;

import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;

public class Info extends Command {
    public static final String name = "info",
            description = "See some informations about the bot",
            usage = null;
    private static final CommandCategory category = CommandCategory.UTILITY;
    private static final boolean requiresArgs = false;
    private static final String[] aliases = null;

    public Info() {
        super(name, description, usage, category, requiresArgs, aliases);
    }

    public void run(CommandContext ctx) {

    }
}
