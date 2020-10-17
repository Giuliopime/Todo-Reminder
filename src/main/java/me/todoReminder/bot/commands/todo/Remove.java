package me.todoReminder.bot.commands.todo;

import me.todoReminder.bot.core.aesthetics.EmbedReplies;
import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;
import me.todoReminder.bot.core.database.DatabaseManager;

public class Remove extends Command {
    public static final String name = "remove",
            description = "Remove a task from a ToDo list" +
                    "\nIf you have multiple ToDo lists you can quickly select one with the flag `--number`" +
                    "\nExample: `t.remove upload YT --1`",
            usage = "[task number / task text] (flags | See t.help remove)";
    private static final CommandCategory category = CommandCategory.TODO;
    private static final boolean requiresArgs = true;
    private static final String[] aliases = {"r"};
    private static final boolean chooseList = true;

    public Remove() {
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
            for(String todo: ctx.getSelectedList().getTodos()) {
                if(todo.toLowerCase().contains(args.toLowerCase())) {
                    todoIndex = ctx.getSelectedList().getTodos().indexOf(todo);
                    break;
                }
            }
        }

        if(todoIndex > -1) {
            ctx.sendMessage(EmbedReplies.infoEmbed(true).setDescription("The following Todo has been removed:\n*"+ctx.getSelectedList().getTodo(todoIndex)+"*"));
            DatabaseManager.getInstance().removeTodo(ctx.getUserID(), ctx.getListIndex(), ctx.getSelectedList().getTodo(todoIndex));
        } else {
            ctx.sendMessage(EmbedReplies.errorEmbed().setDescription("Todo not found. Please provide it's number or some of it's content.\nSee `"+ctx.getPrefix()+"help remove`"));
        }
    }
}
