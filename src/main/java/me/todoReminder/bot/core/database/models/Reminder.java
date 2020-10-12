package me.todoReminder.bot.core.database.models;

import dev.morphia.annotations.Embedded;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

@Embedded
public class Reminder {
    private String reminder;
}
