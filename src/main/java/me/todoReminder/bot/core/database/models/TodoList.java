package me.todoReminder.bot.core.database.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

import java.util.List;

@Entity
public class TodoList {
    @Id
    private ObjectId id;
    private String name;
    private List<String> todos;
    private List<String> completed;

    public TodoList(ObjectId id, String name, List<String> todos, List<String> completed) {
        this.id = id;
        this.name = name;
        this.todos = todos;
        this.completed = completed;
    }

    public String getName() {
        return name;
    }
}
