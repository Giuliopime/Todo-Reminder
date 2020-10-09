package me.todoReminder.bot.commands.utility;

import me.todoReminder.bot.core.aesthetics.EmbedReplies;
import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;
import me.todoReminder.bot.core.commands.CommandHandler;
import net.dv8tion.jda.api.EmbedBuilder;

public class Help extends Command {
    public static final String name = "help",
            description = "Get the bots guide",
            usage = "(command name / command category)";
    private static final CommandCategory category = CommandCategory.UTILITY;
    private static final boolean requiresArgs = false;
    private static final String[] aliases = {"h"};

    public Help() {
        super(name, description, usage, category, requiresArgs, aliases);
    }

    public void run(CommandContext ctx) {
        EmbedBuilder eb = EmbedReplies.infoEmbed();
        if(ctx.getArgs() == null) {
            eb.setTitle("ToDo & Reminder Help")
                    .setDescription("Here is all list of all my commands listed by category:")
                    .setFooter("Use "+ctx.getPrefix()+"help [commandName] for help with a specific command.");

            StringBuilder utilityC = new StringBuilder();
            StringBuilder todoC = new StringBuilder();
            StringBuilder reminderC = new StringBuilder();

            for(Command c: CommandHandler.getCommands()) {
                switch (c.getCategory()) {
                    case UTILITY -> utilityC.append("`").append(c.getName()).append("`, ");

                    case TODO -> todoC.append("`").append(c.getName()).append("`, ");

                    case REMINDER -> reminderC.append("`").append(c.getName()).append("`, ");
                }
            }

            eb.addField("Utility:", utilityC.substring(0, utilityC.length()-2), false);
            eb.addField("ToDo:", todoC.substring(0, todoC.length()-2), false);
            eb.addField("Reminder:", reminderC.substring(0, reminderC.length()-2), false);

            ctx.getTextChannel().sendMessage(eb.build()).queue();
        }
    }
}
