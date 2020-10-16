package me.todoReminder.bot.commands.todo;

import me.todoReminder.bot.core.aesthetics.EmbedReplies;
import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;
import me.todoReminder.bot.core.database.DatabaseManager;
import me.todoReminder.bot.core.database.models.TodoList;

public class Delete extends Command {
    public static final String name = "delete",
            description = "Delete a ToDo list" +
                    "\nIf you have multiple ToDo lists you can quickly select one with the flag `--number`" +
                    "\nExample: `t.delete --1`",
            usage = "(flags | See t.help delete)";
    private static final CommandCategory category = CommandCategory.TODO;
    private static final boolean requiresArgs = true;
    private static final String[] aliases = {"d"};
    private static final boolean chooseList = true;

    public Delete() {
        super(name, description, usage, category, requiresArgs, aliases, chooseList);
    }

    public void run(CommandContext ctx) {
        ctx.sendMessage(EmbedReplies.infoEmbed().setDescription("**The following To Do List has been deleted:**\n"+ctx.getTodoLists().get(ctx.getListIndex()).getName()).build());
        DatabaseManager.getInstance().deleteList(ctx.getUser().getId(), ctx);
    }
}
