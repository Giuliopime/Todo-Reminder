package me.todoReminder.bot.commands.todo;

import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;

public class Delete extends Command {
    public static final String name = "delete",
            description = "Delete a ToDo list" +
                    "\nIf you have multiple ToDo lists you can quickly select one with the flag `--number`" +
                    "\nExample: `t.delete --1`",
            usage = "[task number / task text] (flags | See `t.help delete`)";
    private static final CommandCategory category = CommandCategory.TODO;
    private static final boolean requiresArgs = true;
    private static final String[] aliases = {"d"};

    public Delete() {
        super(name, description, usage, category, requiresArgs, aliases);
    }

    public void run(CommandContext ctx) {

    }
}
