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
            description = "Shows you the list of tasks of a ToDo list" +
                    "\n\nThe possible `(flags)` are:" +
                    "\n`--c` shows all the completed tasks" +
                    "\n`--number` to select a list quickly" +
                    "\n\nExamples:" +
                    "\n`t.show --1` shows all the tasks of the first ToDo list" +
                    "\n`t.show --c --2` shows all the completed tasks of the second ToDo list",
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
            for (String completedTodo : todoList.getCompleted())
                reply.append(count++).append(": ").append(completedTodo).append("\n");
        }
        else {
            for (String todo : todoList.getTodos())
                reply.append(count++).append(": ").append(todo).append("\n");
        }

        reply.append("```");

        ctx.sendMessage(eb.setDescription(reply));
    }
}
