package me.todoReminder.bot.commands.utility;

import ch.qos.logback.core.util.StringCollectionUtil;
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

        String firstArg = ctx.getArgs() != null ? ctx.getArgs().split(" +")[0] : null;

        Command command = null;
        CommandCategory commandCategory = null;
        if(firstArg != null) {
            command = CommandHandler.getCommand(firstArg);

            if(command == null) {
                for(CommandCategory cat: CommandCategory.values()) {
                    if (firstArg.equalsIgnoreCase(cat.name())) {
                        commandCategory = cat;
                        break;
                    }
                }
            }
        }

        if(command == null && commandCategory == null) {
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
        }
        else if(command != null) {
            String aliases = command.getAliases() != null ? "`" + String.join("`, `", command.getAliases()) : "No aliases";
            if(!aliases.equalsIgnoreCase("No aliases")) aliases = aliases.contains("`, `") ? aliases.substring(0, aliases.length()-1) : aliases;
            if(!aliases.endsWith("`")) aliases = aliases + "`";

            eb.setTitle("Command: "+command.getName())
                    .setDescription(command.getDescription())
                    .addField("Usage", command.getUsageExample(ctx.getPrefix()), false)
                    .addField("Aliases", aliases, false)
                    .setFooter("Arguments inside [] parenthesis are mandatory, those inside () parenthesis are optional");
        }
        else if(commandCategory != null) {
            eb.setTitle("Command Category: "+ commandCategory.name().toLowerCase().substring(0, 1).toUpperCase() + commandCategory.name().toLowerCase().substring(1, commandCategory.name().length()));

            StringBuilder commandList = new StringBuilder();
            for(Command c: CommandHandler.getCommands()) {
                if (c.getCategory().equals(commandCategory))
                    commandList.append("`").append(c.getName()).append("`, ");
            }

            commandList.delete(commandList.length() - 2, commandList.length());

            eb.setDescription(commandList);
        }

        ctx.getTextChannel().sendMessage(eb.build()).queue();
    }
}
