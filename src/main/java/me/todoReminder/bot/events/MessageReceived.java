package me.todoReminder.bot.events;

import com.mongodb.DBObject;
import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandContext;
import me.todoReminder.bot.core.commands.CommandHandler;
import me.todoReminder.bot.core.database.DatabaseManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;

public class MessageReceived {
    public static void execute(GuildMessageReceivedEvent guildEvent, MessageReceivedEvent dmEvent) {
        String messageContent, userID;
        if(guildEvent != null) {
            messageContent = guildEvent.getMessage().getContentRaw();
            userID = guildEvent.getAuthor().getId();

            DBObject userData = DatabaseManager.getInstance().getUser(userID);
            String prefix = userData.get("prefix").toString();

            if(!messageContent.startsWith(prefix)) return;

            final String[] splittedMessage = messageContent.substring(prefix.length()).split("/ +/");
            final String commandName = splittedMessage[0];
            String[] args = Arrays.copyOfRange(splittedMessage, 1, splittedMessage.length);

            if(!CommandHandler.isCommand(commandName)) return;

            CommandContext ctx = new CommandContext(guildEvent, dmEvent, commandName, args, userData);
            if(!CommandHandler.checkCategory(ctx)) return;

            if(!CommandHandler.checkArgs(ctx)) return;

            CommandHandler.runCommand(ctx);
        }
        else {
            messageContent = dmEvent.getMessage().getContentRaw();
            userID = dmEvent.getAuthor().getId();
        }
    }
}
