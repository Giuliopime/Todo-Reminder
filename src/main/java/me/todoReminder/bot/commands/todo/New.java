package me.todoReminder.bot.commands.todo;

import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;

public class New extends Command {
    public static final String name = "new",
            description = "Create a new ToDo list",
            usage = "[list name]";
    private static final CommandCategory category = CommandCategory.TODO;
    private static final boolean requiresArgs = true;
    private static final String[] aliases = {"n"};

    public New() {
        super(name, description, usage, category, requiresArgs, aliases);
    }

    public void run(CommandContext ctx) {

    }
}
