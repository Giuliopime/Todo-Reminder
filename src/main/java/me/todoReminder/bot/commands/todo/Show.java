package me.todoReminder.bot.commands.todo;

import me.todoReminder.bot.core.aesthetics.EmbedReplies;
import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;
import me.todoReminder.bot.core.database.schemas.TodoList;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.List;

public class Show extends Command {
    public static final String name = "show",
            description = "Shows you the list of Todos of a Todo list" +
                    "\n\nThe possible `(flags)` are:" +
                    "\n`--c` shows all the completed Todos" +
                    "\n`--number` to select a list quickly" +
                    "\n\nExamples:" +
                    "\n`t.show --1` shows all the Todos of the first Todo list" +
                    "\n`t.show --c --2` shows all the completed Todos of the second Todo list",
            usage = "(flags | See t.help show)";
    private static final CommandCategory category = CommandCategory.TODO;
    private static final boolean requiresArgs = false;
    private static final String[] aliases = {"s"};
    private static final boolean chooseList = true;

    public Show() {
        super(name, description, usage, category, requiresArgs, aliases, chooseList);
    }

    public void run(CommandContext ctx) {
        TodoList todoList = ctx.getSelectedList();

        EmbedBuilder eb = EmbedReplies.infoEmbed(false).setTitle(todoList.getName());
        StringBuilder reply = new StringBuilder().append("```yaml\n");

        int count = 1;
        if(ctx.getArgs() != null && ctx.getArgs().toLowerCase().contains("--c")) {
            eb.setTitle(todoList.getName() + "  [completed]");

            if(todoList.getCompleted().size() == 0)
                reply.append("0 completed Todos found!\n");
            for (String completedTodo : todoList.getCompleted())
                reply.append(count++).append(": ").append(completedTodo).append("\n");
        }
        else {
            if(todoList.getTodos().size() == 0)
                reply.append("0 Todos found!\n");
            for (String todo : todoList.getTodos())
                reply.append(count++).append(": ").append(todo).append("\n");
        }

        reply.append("\n```");

        ctx.sendMessage(eb.setDescription(reply));
    }
}
