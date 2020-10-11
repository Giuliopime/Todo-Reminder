package me.todoReminder.bot.core.database.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

@Entity
public class Reminder {
    @Id
    private ObjectId id;
    private String reminder;
}
