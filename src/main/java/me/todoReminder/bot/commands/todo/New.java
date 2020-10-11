package me.todoReminder.bot.commands.todo;

import me.todoReminder.bot.core.aesthetics.EmbedReplies;
import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;
import me.todoReminder.bot.core.database.DatabaseManager;
import me.todoReminder.bot.core.database.models.TodoList;

import java.util.List;

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
        List<TodoList> todoLists = ctx.getTodoLists();
        if(todoLists != null && todoLists.size() >= 50) {
            ctx.sendMessage(EmbedReplies.infoEmbed().setDescription("You already have 50 ToDo Lists!").build());
            return;
        }
        DatabaseManager.getInstance().newList(ctx.getUser().getId(), ctx.getArgs());

        ctx.sendMessage(EmbedReplies.infoEmbed().setDescription("New ToDo list saved!").build());
    }
}
