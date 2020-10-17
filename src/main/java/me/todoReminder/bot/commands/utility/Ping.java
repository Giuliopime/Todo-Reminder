package me.todoReminder.bot.commands.utility;

import me.todoReminder.bot.core.aesthetics.EmbedReplies;
import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.time.temporal.ChronoUnit;
import java.util.function.Consumer;

public class Ping extends Command {
    private static final String name = "ping",
            description = "Check the bot latency",
            usage = null;
    private static final CommandCategory category = CommandCategory.UTILITY;
    private static final boolean requiresArgs = false;
    private static final String[] aliases = null;

    public Ping() {
        super(name, description, usage, category, requiresArgs, aliases, false);
    }

    public void run(CommandContext ctx) {
        MessageChannel channel = ctx.getChannel();
        channel.sendMessage(EmbedReplies.infoEmbed(false).setDescription("Pinging...").build()).queue(new Consumer<Message>() {
            public void accept(Message m) {
                long ping = ctx.getEvent().getMessage().getTimeCreated().until(m.getTimeCreated(), ChronoUnit.MILLIS);
                m.editMessage(EmbedReplies.infoEmbed(true).setDescription("Ping: "+ping+" ms\nWebsocket: "+ctx.getJda().getGatewayPing()+" ms").setTimestamp(null).build()).queue();
            }
        });
    }
}