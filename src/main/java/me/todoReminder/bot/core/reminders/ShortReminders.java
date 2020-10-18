package me.todoReminder.bot.core.reminders;


import me.todoReminder.bot.core.aesthetics.EmbedReplies;
import net.dv8tion.jda.api.JDA;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ShortReminders {
    private static ShortReminders instance;
    private ScheduledExecutorService scheduledReminders = Executors.newSingleThreadScheduledExecutor();;

    private ShortReminders() { }

    public static ShortReminders getInstance() {
        if(instance == null) {
            instance = new ShortReminders();
        }
        return instance;
    }

    public void shutdown() {
        scheduledReminders.shutdownNow();
    }

    public void newShortReminder(String userID, String reminder, long milliseconds, JDA jda) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                jda.openPrivateChannelById(userID).queue(
                        pvc -> {
                            pvc.sendMessage(EmbedReplies.infoEmbed(true).setTitle("Reminder").setDescription(reminder).build()).queue();
                        });
            }
        };
        scheduledReminders.schedule(task, milliseconds, TimeUnit.MILLISECONDS);
    }
}
