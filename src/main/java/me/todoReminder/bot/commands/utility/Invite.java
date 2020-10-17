package me.todoReminder.bot.commands.utility;

import me.todoReminder.bot.core.aesthetics.EmbedReplies;
import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;

public class Invite extends Command {
    public static final String name = "invite",
            description = "Invite me to another server",
            usage = null;
    private static final CommandCategory category = CommandCategory.UTILITY;
    private static final boolean requiresArgs = false;
    private static final String[] aliases = null;

    public Invite() {
        super(name, description, usage, category, requiresArgs, aliases, false);
    }

    public void run(CommandContext ctx) {
        ctx.sendMessage(EmbedReplies.infoEmbed(false).setTitle("Invite me!", "https://discord.com/oauth2/authorize?client_id=763067629023526954&scope=bot&permissions=289792"));
    }
}
