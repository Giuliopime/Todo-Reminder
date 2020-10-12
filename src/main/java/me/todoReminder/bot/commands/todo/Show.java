package me.todoReminder.bot.commands.todo;

import me.todoReminder.bot.core.aesthetics.EmbedReplies;
import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;
import me.todoReminder.bot.core.database.models.TodoList;
import net.dv8tion.jda.api.EmbedBuilder;

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
    private static final boolean chooseList = true;

    public Show() {
        super(name, description, usage, category, requiresArgs, aliases, chooseList);
    }

    public void run(CommandContext ctx) {
        List<TodoList> todoLists = ctx.getTodoLists();
        TodoList todoList = todoLists.get(ctx.getListIndex());
        StringBuilder reply = new StringBuilder();

        int count = 1;
        for(String todo: todoList.getTodos())
            reply.append(count++).append(". ").append(todo).append("\n");

        ctx.sendMessage(EmbedReplies.infoEmbed().setTitle(todoList.getName()).setDescription(reply).build());
    }
}
