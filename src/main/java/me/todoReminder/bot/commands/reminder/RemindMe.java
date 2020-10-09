package me.todoReminder.bot.commands.reminder;

import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;

public class RemindMe extends Command {
    private static final String name = "remindMe",
            description = "Add a reminder, the bot will DM you a message at the specified time." +
                    "\n\n\nThe `[message]` is the message the bot will send you in the DMs." +
                    "\n\nThe `[time]` is the amount of time the bot will wait until reminding you" +
                    "\nIts format is days-hours-minutes (d-h-m)" +
                    "\n\nThe possible `[flags]` are:" +
                    "\n`--d` to make the reminder daily" +
                    "\n`--w` to make the reminder weekly" +
                    "\n\nExamples:" +
                    "\n`2h0m` will be 2 hours and 30 minutes" +
                    "\n`1d2h` will be 1 day 2 hours" +
                    "\n`2h --d` will remind you every day at the calculated time. For example, if this command is used at 12:00 the bot will remind you every day at 14:00",
            usage ="[message] [time: d-h-m | default is h] (flags | See t.help remindMe)";
    private static final CommandCategory category = CommandCategory.REMINDER;
    private static final boolean requiresArgs = true;
    private static final String[] aliases = {"rm"};

    public RemindMe() {
        super(name, description, usage, category, requiresArgs, aliases);
    }

    public void run(CommandContext ctx) {

    }
}
