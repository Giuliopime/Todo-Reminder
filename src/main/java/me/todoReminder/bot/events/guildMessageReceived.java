package me.todoReminder.bot.events;

import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandContext;
import me.todoReminder.bot.core.commands.CommandHandler;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class guildMessageReceived {
    private String messageContentRaw;
    public guildMessageReceived(GuildMessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;
        messageContentRaw = event.getMessage().getContentRaw();
        String prefix = "t.";
        String commandName = messageContentRaw.substring(prefix.length(), messageContentRaw.length());
        String arguments = messageContentRaw.substring(prefix.length()+commandName.length(), messageContentRaw.length());
        if(CommandHandler.isCommand(commandName)) {
            CommandContext ctx = new CommandContext(event, commandName, arguments);
            CommandHandler.runCommand(ctx);
        }
    }
}
