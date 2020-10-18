package me.todoReminder.bot.commands.reminder;

import me.todoReminder.bot.core.aesthetics.EmbedReplies;
import me.todoReminder.bot.core.commands.Command;
import me.todoReminder.bot.core.commands.CommandCategory;
import me.todoReminder.bot.core.commands.CommandContext;
import me.todoReminder.bot.core.commands.CommandHandler;
import me.todoReminder.bot.core.database.schemas.ReminderSchema;
import me.todoReminder.bot.core.reminders.LongReminders;
import me.todoReminder.bot.core.reminders.ShortReminders;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemindMe extends Command {
    private static final String name = "remindMe",
            description = "Add a reminder, the bot will DM you a message at the specified time." +
                    "\n**Make sure your DMs are open**" +
                    "\n\n\nThe `[message]` is the message the bot will send you in the DMs." +
                    "\n\nThe `[time]` is the time at which the bot will remind you" +
                    "\nIts format is `dd.mm.yyyy hh:mm`" +
                    "\n\nThe possible `[flags]` are:" +
                    "\n`--daily` to make the reminder daily" +
                    "\n`--weekly` to make the reminder weekly",
            usage ="[time] [message] (flags | See t.help remindMe)";
    private static final CommandCategory category = CommandCategory.REMINDER;
    private static final boolean requiresArgs = true;
    private static final String[] aliases = {"rm"};

    // This pattern is from JuniperBot (https://github.com/JuniperBot/JuniperBot/blob/master/jb-worker/src/main/java/ru/juniperbot/worker/commands/RemindCommand.java)
    private static final Pattern PATTERN = Pattern.compile("^(\\d{2}\\.\\d{2}\\.\\d{4})\\s+(\\d{2}:\\d{2})\\s+(.*)$");

    public RemindMe() {
        super(name, description, usage, category, requiresArgs, aliases, false);
    }

    public void run(CommandContext ctx) {
        Matcher m = PATTERN.matcher(ctx.getArgs());

        if(!m.find()) {
            ctx.sendMessage(EmbedReplies.errorEmbed().setDescription("**The reminder is formatted incorrectly**\nSee `"+ctx.getPrefix()+"help remindMe`"));
            return;
        }

        DateTime date = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm").parseDateTime(String.format("%s %s", m.group(1), m.group(2)));
        String reminder = m.group(3);
        if (DateTime.now().isAfter(date)) {
            ctx.sendMessage(EmbedReplies.errorEmbed().setDescription("The time you provided is in the past, I'm not yet able to time-travel sorry."));
            return;
        }

        boolean daily = false, weekly = false;
        long reminderTime = date.getMillis();
        System.out.println(reminderTime);
        if(reminder.contains("--daily")) {
            daily = true;
            reminder = reminder.replace("--daily", "");
        }
        if(reminder.contains("--weekly")) {
            weekly = true;
            reminder = reminder.replace("--weekly", "");
        }

        long currentTimeMS = System.currentTimeMillis();
        System.out.println(currentTimeMS);
        if(reminderTime - currentTimeMS < 120000) {
            ShortReminders.getInstance().newShortReminder(ctx.getUserID(), reminder, reminderTime - currentTimeMS, ctx.getJda());

            reminderTime = 0;
            if(daily)
                reminderTime = reminderTime + 86400000;
            else if(weekly)
                reminderTime = reminderTime + 604800000;
        }

        if(reminderTime != 0) {
            ReminderSchema reminderSchema = new ReminderSchema(ctx.getUserID(), reminder, reminderTime, daily, weekly);
            LongReminders.getInstance(ctx.getJda()).newReminder(reminderSchema);
        }

        ctx.sendMessage(EmbedReplies.infoEmbed(true).setDescription("**Reminder set!**\n\n*Make sure your DMs are open*"));
    }
}
