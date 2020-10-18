package me.todoReminder.bot.core.database.schemas;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

@Entity("reminders")
public class ReminderSchema {
    @Id
    private ObjectId id;
    private String userID;
    private String reminder;
    private long milliseconds;
    private boolean daily, weekly;

    public ReminderSchema() { }

    public ReminderSchema(String userID, String reminder, long milliseconds, boolean daily, boolean weekly) {
        id = new ObjectId();
        this.userID = userID;
        this.reminder = reminder;
        this.milliseconds = milliseconds;
        this.daily = daily;
        this.weekly = weekly;
    }

    public String getId() {
        return id.toString();
    }

    public String getUserID() {
        return userID;
    }

    public String getReminder() {
        return reminder;
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    public boolean isDaily() {
        return daily;
    }

    public boolean isWeekly() {
        return weekly;
    }

    public void setMilliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }
}
