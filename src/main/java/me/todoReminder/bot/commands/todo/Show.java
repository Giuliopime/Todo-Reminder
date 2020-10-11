package me.todoReminder.bot.commands.todo;

import me.todoReminder.bot.core.aesthetics.EmbedReplies;
import me.todoReminder.bot.core.aesthetics.Emojis;
import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;
import me.todoReminder.bot.core.database.models.TodoList;

import java.util.List;

public class Show extends Command {
    public static final String name = "show",
            description = "Shows you the list of tasks of a ToDo list" +
                    "\n\nThe possible `[flags]` are:" +
                    "\n`--r` shows all the removed tasks" +
                    "\n`--c` shows all the completed tasks" +
                    "\n`--number` to select a list quickly" +
                    "\n\nExamples:" +
                    "\n`t.show --1` shows all the tasks of the first ToDo list" +
                    "\n`t.show --c -2` shows all the completed tasks of the second ToDo list",
            usage = "(flags | See t.help show)";
    private static final CommandCategory category = CommandCategory.TODO;
    private static final boolean requiresArgs = false;
    private static final String[] aliases = {"s"};

    public Show() {
        super(name, description, usage, category, requiresArgs, aliases);
    }

    public void run(CommandContext ctx) {
        List<TodoList> todoLists = ctx.getTodoLists();

        if(todoLists.size() == 0) {
            ctx.sendMessage(EmbedReplies.infoEmbed().setDescription("You don't have any ToDo List!").build());
            return;
        }

        StringBuilder reply = new StringBuilder().append("Here is a list of all your ToDo Lists");
        int count = 0;
        for(TodoList todoList: todoLists) {
            reply.append(count++).append(" ").append(todoList.getName()).append("\n");
        }

        ctx.sendMessage(EmbedReplies.infoEmbed().setDescription(reply).build());
    }
}
