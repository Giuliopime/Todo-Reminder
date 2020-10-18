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
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.spi.TimeZoneNameProvider;

public class RemindMe extends Command {
    private static final String name = "remindMe",
            description = "Add a reminder, the bot will DM you a message at the specified time." +
                    "\n**Make sure your DMs are open**" +
                    "\n\n\nThe `[message]` is the message the bot will send you in the DMs." +
                    "\n\nThe `[time]` is the time at which the bot will remind you" +
                    "\nIts format is `days hours minutes`: `d h m`" +
                    "\n\nThe possible `[flags]` are:" +
                    "\n`--daily` to make the reminder daily" +
                    "\n`--weekly` to make the reminder weekly" +
                    "\n\nExample usage:" +
                    "\n`t.remindMe 1h 2m Do something` this will set a reminder in 1 hour and 2 minutes",
            usage ="[time] [message] (flags | See t.help remindMe)";
    private static final CommandCategory category = CommandCategory.REMINDER;
    private static final boolean requiresArgs = true;
    private static final String[] aliases = {"rm"};

    private static final Pattern DAYS = Pattern.compile("(\\d{1,2})d", Pattern.CASE_INSENSITIVE);
    private static final Pattern HOURS = Pattern.compile("(\\d{1,2})h", Pattern.CASE_INSENSITIVE);
    private static final Pattern MINUTES = Pattern.compile("(\\d{1,2})m", Pattern.CASE_INSENSITIVE);

    public RemindMe() {
        super(name, description, usage, category, requiresArgs, aliases, false);
    }

    public void run(CommandContext ctx) {
        // This is probably shit ik  ¯\_(ツ)_/¯
        String reminder = ctx.getArgs();

        Matcher daysMatcher = DAYS.matcher(reminder);
        Matcher hoursMatcher = HOURS.matcher(reminder);
        Matcher minutesMatcher = MINUTES.matcher(reminder);

        boolean foundDays = daysMatcher.find(),
                foundHours = hoursMatcher.find(),
                foundMinutes = minutesMatcher.find();

        if(!foundDays && !foundHours && !foundMinutes) {
            ctx.sendMessage(EmbedReplies.errorEmbed().setDescription("**The reminder is formatted incorrectly**\nSee `"+ctx.getPrefix()+"help remindMe`"));
            return;
        }

        int days = 0, hours = 0, minutes = 0;

        if(foundDays) {
            days = Integer.parseInt(daysMatcher.group(1));
            reminder = reminder.replace(daysMatcher.group(1), "");
        }
        if(foundHours) {
            hours = Integer.parseInt(hoursMatcher.group(1));
            reminder = reminder.replace(hoursMatcher.group(1), "");
        }
        if(foundMinutes) {
            minutes = Integer.parseInt(minutesMatcher.group(1));
            reminder = reminder.replace(minutesMatcher.group(1), "");
        }

        boolean daily = false, weekly = false;

        long reminderTime = System.currentTimeMillis() + (days * 86400000) + (hours * 3600000) + (minutes * 60000);


        if(reminder.contains("--daily")) {
            daily = true;
            reminder = reminder.replace("--daily", "");
        }
        if(reminder.contains("--weekly")) {
            weekly = true;
            reminder = reminder.replace("--weekly", "");
        }

        long currentTimeMS = System.currentTimeMillis();

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
