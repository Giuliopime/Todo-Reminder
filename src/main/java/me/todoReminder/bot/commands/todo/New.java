package me.todoReminder.bot.commands.todo;

import me.todoReminder.bot.core.aesthetics.EmbedReplies;
import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;
import me.todoReminder.bot.core.database.DatabaseManager;
import me.todoReminder.bot.core.database.schemas.TodoList;

import java.util.List;

public class New extends Command {
    public static final String name = "new",
            description = "Create a new Todo list",
            usage = "[list name]";
    private static final CommandCategory category = CommandCategory.TODO;
    private static final boolean requiresArgs = true;
    private static final String[] aliases = {"n"};
    private static final boolean chooseList = false;

    public New() {
        super(name, description, usage, category, requiresArgs, aliases, chooseList);
    }

    public void run(CommandContext ctx) {
        List<TodoList> todoLists = ctx.getTodoLists();
        if(todoLists != null && todoLists.size() >= 50) {
            ctx.sendMessage(EmbedReplies.errorEmbed().setDescription("You already have 50 Todo Lists!"));
            return;
        }

        DatabaseManager.getInstance().newList(ctx.getUserID(), ctx.getArgs());
        ctx.sendMessage(EmbedReplies.infoEmbed(true).setDescription("New Todo list saved!"));
    }
}
