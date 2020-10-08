package me.todoReminder.bot.commands.todo;

import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;

public class Complete extends Command {
    public static final String name = "complete",
            description = "Mark a task as completed" +
                    "\nIf you have multiple ToDo lists you can quickly select one with the flag `--number`" +
                    "\nExample: `t.complete upload YT --1`",
            usage = "[task number / task text] (flags | See `t.help complete`)";
    private static final CommandCategory category = CommandCategory.TODO;
    private static final boolean requiresArgs = true;
    private static final String[] aliases = {"c"};

    public Complete() {
        super(name, description, usage, category, requiresArgs, aliases);
    }

    public void run(CommandContext ctx) {

    }
}
