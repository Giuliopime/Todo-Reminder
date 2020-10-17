package me.todoReminder.bot.core.database.schemas;

import dev.morphia.annotations.Embedded;

@Embedded
public class Reminder {
    private String reminder;
}
