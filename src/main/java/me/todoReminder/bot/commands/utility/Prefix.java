package me.todoReminder.bot.commands.utility;

import me.todoReminder.bot.core.aesthetics.EmbedReplies;
import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;
import me.todoReminder.bot.core.database.DatabaseManager;
import net.dv8tion.jda.api.Permission;

public class Prefix extends Command {
    public static final String name = "prefix",
            description = "See my prefix or change it with `t.prefix [new prefix]`",
            usage = "[new prefix]";
    private static final CommandCategory category = CommandCategory.UTILITY;
    private static final boolean requiresArgs = false;
    private static final String[] aliases = {"p"};

    public Prefix() {
        super(name, description, usage, category, requiresArgs, aliases, false);
    }

    public void run(CommandContext ctx) {
        if(!ctx.getEvent().isFromGuild()) {
            ctx.sendMessage(EmbedReplies.errorEmbed().setDescription("You can only execute this command inside a server"));
            return;
        }
        if(ctx.getArgs() == null) {
            String prefix = ctx.getPrefix();
            ctx.sendMessage(EmbedReplies.infoEmbed(false)
                    .setTitle("Command: prefix")
                    .setDescription("My prefix here is `"+prefix+"`" +
                            "\nYou can change it with `"+prefix+"prefix [new prefix]`")
            );
        } else {
            if(!ctx.getEvent().getMember().hasPermission(Permission.MANAGE_SERVER)) {
                ctx.sendMessage(EmbedReplies.errorEmbed().setDescription("You need `Manage Server` permissions to use this command."));
                return;
            }

            String prefix = String.join(" ", ctx.getArgs());
            if(prefix.length() > 10) {
                ctx.sendMessage(EmbedReplies.errorEmbed().setDescription("The prefix can't be longer than 10 characters."));
                return;
            }

            DatabaseManager.getInstance().setPrefix(ctx.getUserID(), prefix);
            ctx.sendMessage(EmbedReplies.infoEmbed(true).setDescription("Prefix set to `"+prefix+"`."));
        }
    }
}
