package me.todoReminder.bot.events;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import me.todoReminder.bot.Config;
import me.todoReminder.bot.core.aesthetics.EmbedReplies;
import me.todoReminder.bot.core.cache.CacheManager;
import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;
import me.todoReminder.bot.core.commands.CommandHandler;
import me.todoReminder.bot.core.database.DatabaseManager;
import me.todoReminder.bot.core.database.schemas.TodoList;
import me.todoReminder.bot.core.database.schemas.UserSchema;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageReceived {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceived.class);

    public static void execute(MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;

        String messageContent = event.getMessage().getContentRaw(),
                userID = event.getAuthor().getId();
        boolean isFromGuild = event.isFromGuild();
        // Get the user data (all to-do lists and reminders) from the database
        UserSchema userData = DatabaseManager.getInstance().getUser(userID);

        /*
            Check if the message is a valid command:
            The user can execute messages in the bot DMs without a prefix
            while if the command is executed in a guild it needs to start either with the prefix or the bot mention
         */
        String prefix = "";
        if(isFromGuild) prefix = DatabaseManager.getInstance().getPrefix(event.getGuild().getId());
        CommandContext ctx = new CommandContext(event, null, null, userData, 0, prefix);

        if(isFromGuild && !messageContent.startsWith(prefix)) {
            if(messageContent.startsWith("<@!"+event.getJDA().getSelfUser().getId()+">"))
                prefix =  "<@!"+event.getJDA().getSelfUser().getId()+">";
            else if(messageContent.startsWith("<@"+event.getJDA().getSelfUser().getId()+">"))
                prefix =  "<@"+event.getJDA().getSelfUser().getId()+">";
            else return;
        }

        String withoutPrefix = messageContent.substring(prefix.length()).trim();
        String commandName = withoutPrefix.split(" +")[0];
        String args = withoutPrefix.substring(commandName.length()).trim();
        if(args.isEmpty()) args = null;

        commandName = CommandHandler.isCommand(commandName);
        if(commandName == null) return;

        long cooldownTimestamp = CacheManager.getInstance().isOnCooldown(userID);
        if(cooldownTimestamp != 0) {
            ctx.sendMessage(EmbedReplies.onCooldownEmbed(cooldownTimestamp));
            return;
        }

        // Update the command context
        ctx.setCommandName(commandName);
        ctx.setArgs(args);

        if(!CommandHandler.checkArgs(ctx)) return;


        /*
        Various checks depending on the category of the command:
        - developer commands can only be executed by the owner (defined in .env) :bonk:
        - most to-do commands require the user to choose a to-do list
         */
        Command command = CommandHandler.getCommand(ctx.getCommandName());

        if(command.getCategory() == CommandCategory.DEVELOPER)
            if(!ctx.getUserID().equalsIgnoreCase(Config.get("OWNER"))) {
                ctx.sendMessage(EmbedReplies.errorEmbed().setDescription("This command is restricted to the developers of the bot."));
                return;
            }

        if(command.getCategory() == CommandCategory.TODO && command.requiresListChoice()) {
            if(ctx.getTodoLists().isEmpty()) {
                ctx.sendMessage(EmbedReplies.infoEmbed(false).setDescription("**You don't have any Todo List yet.**\nCreate one with `" + ctx.getPrefix() + "new`!"));
            }
            else if(ctx.getTodoLists().size() == 1) {
                CommandHandler.runCommand(ctx);
            }
            else {
                /*
                This checks if the message contains a flag to indicate the to-do list to choose (example: --3)
                If the message doesn't contain a flag it will start a message collector to ask the user to select a to-do list
                 */
                if (args == null) args = "";
                Matcher matcher = Pattern.compile("(--\\d+)").matcher(args);
                if (matcher.find()) {
                    // Check if the provided number is a valid list (not out of bounds)
                    int listIndex = Integer.parseInt(matcher.group(1).substring(2)) - 1;
                    if(listIndex >= ctx.getTodoLists().size()) {
                        ctx.sendMessage(EmbedReplies.errorEmbed().setDescription("The number you provided is not a valid Todo List number"));
                        return;
                    }
                    // Sets the index of the list (which is the user choice minus one)
                    ctx.setListIndex(listIndex);
                    // Removes the flag from the arguments
                    ctx.setArgs(args.replace(matcher.group(1), "").trim());

                    CommandHandler.runCommand(ctx);
                } else {
                    StringBuilder choiceMessage = new StringBuilder().append("**Select one of the following Todo List:**\n```yaml\n");
                    int optionNumber = 1;

                    for (TodoList todoList : ctx.getTodoLists())
                        choiceMessage.append(optionNumber++).append(": ").append(todoList.getName()).append("\n");
                    choiceMessage.append("```");

                    event.getChannel().sendMessage(EmbedReplies.infoEmbed(false).setDescription(choiceMessage).setFooter("To choose a Todo List simply type its number in the chat").build()).queue((message) -> {
                        EventWaiter waiter = EventManager.getWaiter();
                        waiter.waitForEvent(MessageReceivedEvent.class,
                                e -> {
                                    if (e.getAuthor().isBot()) return false;
                                    if(!e.getChannel().getId().equalsIgnoreCase(ctx.getChannel().getId()) || !e.getAuthor().getId().equalsIgnoreCase(ctx.getUserID())) return false;

                                    int listIndex = 0;
                                    try {
                                        listIndex = Integer.parseInt(e.getMessage().getContentRaw());
                                    } catch (Exception ignored) { }

                                    if (listIndex <= 0 || listIndex > ctx.getTodoLists().size()) {
                                        e.getChannel().sendMessage(EmbedReplies.errorEmbed().setDescription("That is not a valid number of a Todo List!\nTry again.").build()).queue();
                                        return false;
                                    }
                                    return true;
                                },
                                e -> {
                                    message.delete().queue();
                                    e.getMessage().delete().queue();
                                    ctx.setListIndex(Integer.parseInt(e.getMessage().getContentRaw()) - 1);
                                    CommandHandler.runCommand(ctx);
                                },

                                1, TimeUnit.MINUTES, () -> ctx.sendMessage(EmbedReplies.errorEmbed().setDescription("You took too long.")));
                    });
                }
            }
        }
        else CommandHandler.runCommand(ctx);;
    }
}
