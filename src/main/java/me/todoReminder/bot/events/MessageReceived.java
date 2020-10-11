package me.todoReminder.bot.events;

import me.todoReminder.bot.core.commands.CommandContext;
import me.todoReminder.bot.core.commands.CommandHandler;
import me.todoReminder.bot.core.database.DatabaseManager;
import me.todoReminder.bot.core.database.models.UserModel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

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

        ctx = new CommandContext(event, commandName, args, userData);

        if(!CommandHandler.checkCategory(ctx)) return;

        if(!CommandHandler.checkArgs(ctx)) return;

        CommandHandler.runCommand(ctx);
    }
}
