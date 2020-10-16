package me.todoReminder.bot.commands.todo;

import me.todoReminder.bot.core.aesthetics.EmbedReplies;
import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;
import me.todoReminder.bot.core.database.models.TodoList;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.List;

public class Lists extends Command {
    public static final String name = "lists",
            description = "Shows you all your ToDo Lists",
            usage = null;
    private static final CommandCategory category = CommandCategory.TODO;
    private static final boolean requiresArgs = false;
    private static final String[] aliases = {"l"};
    private static final boolean chooseList = false;

    public Lists() {
        super(name, description, usage, category, requiresArgs, aliases, chooseList);
    }

    public void run(CommandContext ctx) {
        StringBuilder reply = new StringBuilder();

        int count = 1;
        for(TodoList todoList: ctx.getTodoLists()) {
            reply.append(count++).append(". ").append(todoList.getName()).append("\n");
        }

        ctx.sendMessage(EmbedReplies.infoEmbed().setTitle("All your ToDo Lists").setDescription(reply).build());
    }
}