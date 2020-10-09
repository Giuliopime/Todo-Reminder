package me.todoReminder.bot.events;

import com.mongodb.DBObject;
import jdk.jfr.DataAmount;
import me.todoReminder.bot.core.commands.CommandContext;
import me.todoReminder.bot.core.commands.CommandHandler;
import me.todoReminder.bot.core.database.DatabaseManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.bson.Document;

public class MessageReceived {
    public static void execute(GuildMessageReceivedEvent guildEvent, MessageReceivedEvent dmEvent) {
        String messageContent, userID;
        CommandContext ctx;

        if(guildEvent != null) {
            messageContent = guildEvent.getMessage().getContentRaw();
            userID = guildEvent.getAuthor().getId();
        } else {
            messageContent = dmEvent.getMessage().getContentRaw();
            userID = dmEvent.getAuthor().getId();
        }

        Document userData = DatabaseManager.getInstance().getUser(userID);;

        // Check the prefix (not required in DMs)
        String prefix = userData.get("prefix").toString();
        if(!messageContent.startsWith(prefix)) {
            if(guildEvent != null) return;
            else prefix = "";
        }

        String commandName = messageContent.substring(prefix.length()).split(" +")[0];
        String args = messageContent.substring(prefix.length() + commandName.length()).trim();
        if(args.isEmpty()) args = null;

        commandName = CommandHandler.isCommand(commandName);
        if(commandName == null) return;

        ctx = new CommandContext(guildEvent, dmEvent, commandName, args, userData);

        if(!CommandHandler.checkCategory(ctx)) return;

        if(!CommandHandler.checkArgs(ctx)) return;

        CommandHandler.runCommand(ctx);
    }
}
