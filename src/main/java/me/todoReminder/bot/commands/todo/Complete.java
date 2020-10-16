package me.todoReminder.bot.commands.todo;

import me.todoReminder.bot.core.aesthetics.EmbedReplies;
import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;
import me.todoReminder.bot.core.database.DatabaseManager;
import me.todoReminder.bot.core.database.models.TodoList;

public class Complete extends Command {
    public static final String name = "complete",
            description = "Mark a task as completed" +
                    "\nIf you have multiple ToDo lists you can quickly select one with the flag `--number`" +
                    "\nExample: `t.complete upload YT --1`",
            usage = "[task number / task text] (flags | See t.help complete)";
    private static final CommandCategory category = CommandCategory.TODO;
    private static final boolean requiresArgs = true;
    private static final String[] aliases = {"c"};
    private static final boolean chooseList = true;

    public Complete() {
        super(name, description, usage, category, requiresArgs, aliases, chooseList);
    }

    public void run(CommandContext ctx) {
        int todoIndex = -1;
        String args = ctx.getArgs();

        int number = 0;
        try{
            number = Integer.parseInt(args);
        } catch(Exception ignored) {}

        if(number > 0 && number < ctx.getTodoLists().get(ctx.getListIndex()).getTodos().size() + 1)
            todoIndex = number - 1;
        else {
            for(String todo: ctx.getTodoLists().get(ctx.getListIndex()).getTodos()) {
                if(todo.toLowerCase().contains(args.toLowerCase())) {
                    todoIndex = ctx.getTodoLists().get(ctx.getListIndex()).getTodos().indexOf(todo);
                    break;
                }
            }
        }

        if(todoIndex > -1) {
            ctx.sendMessage(EmbedReplies.infoEmbed().setDescription("The following To Do has been marked as completed:\n\n"+ctx.getTodoLists().get(ctx.getListIndex()).getTodos().get(todoIndex)).build());
            DatabaseManager.getInstance().completeTodo(ctx.getUser().getId(), ctx.getListIndex(), ctx.getTodoLists().get(ctx.getListIndex()).getTodo(todoIndex));
        } else {
            ctx.sendMessage(EmbedReplies.warningEmbed().setDescription("To Do not found. Please provide it's number or some of it's content.\nSee `"+ctx.getPrefix()+"help complete`").build());
        }
    }
}
