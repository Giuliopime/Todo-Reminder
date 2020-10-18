package me.todoReminder.bot.commands.todo;

import me.todoReminder.bot.core.aesthetics.EmbedReplies;
import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;
import me.todoReminder.bot.core.database.DatabaseManager;

public class Add extends Command {
    public static final String name = "add",
            description = "Add a Todo to a Todo list" +
                    "\nIf you have multiple Todo lists you can quickly select one with the flag `--number`" +
                    "\nExample: `t.add upload YT video --1`",
            usage = "[Todo's text] (flags | See t.help add)";
    private static final CommandCategory category = CommandCategory.TODO;
    private static final boolean requiresArgs = true;
    private static final String[] aliases = {"a"};
    private static final boolean chooseList = true;

    public Add() {
        super(name, description, usage, category, requiresArgs, aliases, chooseList);
    }

    public void run(CommandContext ctx) {
        if(ctx.getTodoLists().get(ctx.getListIndex()).getTodos().size() >= 100) {
            ctx.sendMessage(EmbedReplies.errorEmbed().setDescription("You already have 100 Todos in that list!"));
            return;
        }
        DatabaseManager.getInstance().addTodo(ctx.getUserID(), ctx.getListIndex(), ctx.getArgs());

        ctx.sendMessage(EmbedReplies.infoEmbed(true).setDescription("Todo added!"));
    }
}