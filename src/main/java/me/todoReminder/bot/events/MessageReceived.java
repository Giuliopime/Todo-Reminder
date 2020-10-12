package me.todoReminder.bot.events;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import me.todoReminder.bot.Config;
import me.todoReminder.bot.core.EventManager;
import me.todoReminder.bot.core.aesthetics.EmbedReplies;
import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;
import me.todoReminder.bot.core.commands.CommandHandler;
import me.todoReminder.bot.core.database.DatabaseManager;
import me.todoReminder.bot.core.database.models.TodoList;
import me.todoReminder.bot.core.database.models.UserModel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageReceived {
    public static void execute(MessageReceivedEvent event) {
        String messageContent = event.getMessage().getContentRaw(), userID = event.getAuthor().getId();
        CommandContext ctx;

        UserModel userData = DatabaseManager.getInstance().getUser(userID);;

        // Check the prefix (not required in DMs)
        String prefix = userData.getPrefix();
        if(!messageContent.startsWith(prefix)) {
            if(event.isFromGuild()) return;
            else prefix = "";
        }

        String commandName = messageContent.substring(prefix.length()).split(" +")[0];
        String args = messageContent.substring(prefix.length() + commandName.length()).trim();
        if(args.isEmpty()) args = null;

        commandName = CommandHandler.isCommand(commandName);
        if(commandName == null) return;

        ctx = new CommandContext(event, commandName, args, userData, 0);

        if(!CommandHandler.checkArgs(ctx)) return;

        Command command = CommandHandler.getCommand(ctx.getCommandName());

        if(command.getCategory() == CommandCategory.DEVELOPER)
            if(!ctx.getUser().getId().equalsIgnoreCase(Config.get("OWNER"))) {
                ctx.sendMessage(EmbedReplies.warningEmbed().setDescription("This command is restricted to the developers of the bot.").build());
                 return;
            }

        if(command.getCategory() == CommandCategory.TODO && command.getChooseList() && ctx.getTodoLists().size() != 1) {
            if (args == null) args = "";
            Pattern pattern = Pattern.compile("(--\\d+)");
            Matcher matcher = pattern.matcher(args);
            if (matcher.find()) {
                String choice = matcher.group(1);
                ctx.setListIndex(Integer.parseInt(choice.substring(2)) - 1);
                ctx.setArgs(args.replace(choice, "").trim());
                CommandHandler.runCommand(ctx);
            } else {
                StringBuilder choiceMessage = new StringBuilder().append("**Please select one of the following ToDo List:**\n");
                int optionNumber = 1;
                for (TodoList todoList : ctx.getTodoLists())
                    choiceMessage.append(optionNumber++).append(". ").append(todoList.getName()).append("\n");

                ctx.getChannel().sendMessage(EmbedReplies.infoEmbed().setDescription(choiceMessage.toString()).build()).queue((message) -> {
                    EventWaiter waiter = EventManager.getWaiter();
                    waiter.waitForEvent(MessageReceivedEvent.class,
                            e -> {
                                if(e.getAuthor().isBot()) return false;
                                String m = e.getMessage().getContentRaw();
                                int listIndex = 0;
                                try {
                                    listIndex = Integer.parseInt(m);
                                } catch (Exception ignored) { }
                                if (listIndex <= 0 || listIndex > ctx.getTodoLists().size()) {
                                    e.getChannel().sendMessage(EmbedReplies.warningEmbed().setDescription("That is not a valid number of a ToDo List!").build()).queue();
                                    return false;
                                }
                                return e.getChannel().getId().equalsIgnoreCase(ctx.getChannel().getId()) && e.getAuthor().getId().equalsIgnoreCase(ctx.getUser().getId());
                            },
                            e -> {
                                message.delete().queue();
                                e.getMessage().delete().queue();
                                ctx.setListIndex(Integer.parseInt(e.getMessage().getContentRaw()) - 1);
                                CommandHandler.runCommand(ctx);
                            },

                            1, TimeUnit.MINUTES, () -> ctx.sendMessage(EmbedReplies.warningEmbed().setDescription("You took too long.").build()));
                });
            }
        }
        else CommandHandler.runCommand(ctx);
    }
}
