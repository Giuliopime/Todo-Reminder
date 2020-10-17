package me.todoReminder.bot.commands.todo;

import me.todoReminder.bot.core.aesthetics.EmbedReplies;
import me.todoReminder.bot.core.aesthetics.Emojis;
import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;
import me.todoReminder.bot.core.database.DatabaseManager;

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

        if(number > 0 && number < ctx.getSelectedList().getTodos().size() + 1)
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
            DatabaseManager.getInstance().completeTodo(ctx.getUserID(), ctx.getListIndex(), ctx.getSelectedList().getTodo(todoIndex));
            ctx.sendMessage(EmbedReplies.infoEmbed(true).setDescription("**The following Todo has been marked as completed:**\n\n*"+ Emojis.completed+" "+ctx.getSelectedList().getTodo(todoIndex)+"*"));
        } else {
            ctx.sendMessage(EmbedReplies.errorEmbed().setDescription("Todo not found. Please provide it's number or some of it's content.\nSee `"+ctx.getPrefix()+"help complete`"));
        }
    }
}
