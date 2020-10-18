package me.todoReminder.bot.core.reminders;

import me.todoReminder.bot.core.aesthetics.EmbedReplies;
import me.todoReminder.bot.core.cache.CacheManager;
import me.todoReminder.bot.core.database.DatabaseManager;
import me.todoReminder.bot.core.database.schemas.ReminderSchema;
import net.dv8tion.jda.api.JDA;
import org.joda.time.DateTime;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LongReminders {
    private static LongReminders instance;
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private final JDA jda;

    private LongReminders(JDA jda) {
        CacheManager.getInstance().setAllReminders();
        this.jda = jda;
    }

    public static LongReminders getInstance(JDA jda) {
        if(instance == null) {
            instance = new LongReminders(jda);
        }
        return instance;
    }

    public void shutdown() {
        executor.shutdownNow();
    }

    public void run() {
        Runnable sendReminders = () -> {
            List<ReminderSchema> reminders = CacheManager.getInstance().getAllReminders();
            for(ReminderSchema reminder: reminders) {
                if(DateTime.now().getMillis() >= reminder.getMilliseconds()) {
                    jda.openPrivateChannelById(reminder.getUserID()).queue(
                            pvc -> {
                                pvc.sendMessage(EmbedReplies.infoEmbed(true).setTitle("Reminder").setDescription(reminder.getReminder()).build()).queue();
                            }
                    );

                    removerReminder(reminder.getId());

                    if (reminder.isDaily() || reminder.isWeekly()) {
                        if (reminder.isDaily())
                            reminder.setMilliseconds(reminder.getMilliseconds() + 86400000);

                        else if (reminder.isWeekly())
                            reminder.setMilliseconds(reminder.getMilliseconds() + 604800000);

                        newReminder(reminder);
                    }
                }
            }
        };
        executor.scheduleAtFixedRate(sendReminders, 0, 1, TimeUnit.MINUTES);
    }

    public void newReminder(ReminderSchema reminderSchema) {
        DatabaseManager.getInstance().saveReminder(reminderSchema);
        CacheManager.getInstance().addReminder(reminderSchema);
    }

    public void removerReminder(String reminderID) {
        DatabaseManager.getInstance().removeReminder(reminderID);
        CacheManager.getInstance().removeReminder(reminderID);
    }
}
