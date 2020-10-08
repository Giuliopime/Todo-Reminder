package me.todoReminder.bot.commands.utility;

import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;

public class Help extends Command {
    public static final String name = "help",
            description = "Get the bots guide",
            usage = "(command name / command category)";
    private static final CommandCategory category = CommandCategory.UTILITY;
    private static final boolean requiresArgs = false;
    private static final String[] aliases = {"h"};

    public Help() {
        super(name, description, usage, category, requiresArgs, aliases);
    }

    public void run(CommandContext ctx) {

    }
}
