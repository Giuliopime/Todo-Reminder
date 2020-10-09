package me.todoReminder.bot.commands.todo;

import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;

public class Add extends Command {
    public static final String name = "add",
            description = "Add a task to a ToDo list" +
                    "\nIf you have multiple ToDo lists you can quickly select one with the flag `--number`" +
                    "\nExample: `t.add upload YT video --1`",
            usage = "[task's text] (flags | See t.help add)";
    private static final CommandCategory category = CommandCategory.TODO;
    private static final boolean requiresArgs = true;
    private static final String[] aliases = {"a"};

    public Add() {
        super(name, description, usage, category, requiresArgs, aliases);
    }

    public void run(CommandContext ctx) {

    }
}