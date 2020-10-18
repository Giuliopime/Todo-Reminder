package me.todoReminder.bot.commands.utility;

import me.todoReminder.bot.core.aesthetics.EmbedReplies;
import me.todoReminder.bot.core.aesthetics.Emojis;
import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.SelfUser;

public class Info extends Command {
    public static final String name = "info",
            description = "See some informations about the bot",
            usage = null;
    private static final CommandCategory category = CommandCategory.UTILITY;
    private static final boolean requiresArgs = false;
    private static final String[] aliases = null;

    public Info() {
        super(name, description, usage, category, requiresArgs, aliases, false);
    }

    public void run(CommandContext ctx) {
        JDA jda = ctx.getJda();
        EmbedBuilder eb = EmbedReplies.infoEmbed(false)
                .setAuthor(jda.getSelfUser().getName())
                .setDescription(Emojis.online+"Version: `Beta`\n" +
                        Emojis.online+"Library: `JDA`\n" +
                        Emojis.online+"Developer: `</> Giuliopime#4965`\n" +
                        Emojis.online+"Servers: `"+jda.getGuilds()+"`\n");
        ctx.sendMessage(eb);
    }
}
