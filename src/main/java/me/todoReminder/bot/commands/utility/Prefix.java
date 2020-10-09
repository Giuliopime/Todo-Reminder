package me.todoReminder.bot.commands.utility;

import me.todoReminder.bot.core.aesthetics.EmbedReplies;
import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;
import me.todoReminder.bot.core.database.DatabaseManager;

public class Prefix extends Command {
    public static final String name = "prefix",
            description = "See my prefix or change it with `t.prefix [new prefix]`\nPrefixes are USER BASED",
            usage = "[new prefix]";
    private static final CommandCategory category = CommandCategory.UTILITY;
    private static final boolean requiresArgs = false;
    private static final String[] aliases = {"p"};

    public Prefix() {
        super(name, description, usage, category, requiresArgs, aliases);
    }

    public void run(CommandContext ctx) {
        if(ctx.getArgs() == null) {
            String prefix = ctx.getPrefix();
            ctx.getTextChannel().sendMessage(EmbedReplies.infoEmbed()
                    .setTitle("Command: prefix")
                    .setDescription("Your prefix is `"+prefix+"`" +
                            "\nYou can change it with `"+prefix+"prefix [new prefix]`")
                    .build()).queue();
        } else {
            String prefix = String.join(" ", ctx.getArgs());
            if(prefix.length() > 10) {
                ctx.getTextChannel().sendMessage(EmbedReplies.errorEmbed().setDescription("The prefix can't be longer than 10 characters.").build()).queue();
                return;
            }

            DatabaseManager.getInstance().setPrefix(ctx.getMember().getId(), prefix);
            ctx.getTextChannel().sendMessage(EmbedReplies.infoEmbed().setDescription("Prefix set to `"+prefix+"`.").build()).queue();
        }
    }
}
