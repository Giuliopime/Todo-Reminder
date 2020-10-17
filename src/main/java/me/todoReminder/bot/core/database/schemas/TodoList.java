package me.todoReminder.bot.core.database.schemas;

import dev.morphia.annotations.Embedded;

import java.util.ArrayList;
import java.util.List;
@Embedded
public class TodoList {
    private String name;
    private List<String> todos;
    private List<String> completed;

    public TodoList() { }

    public TodoList(String name) {
        this.name = name;
        todos = new ArrayList<>();
        completed = new ArrayList<>();
    }

    // Getters
    public String getName() {
        return name;
    }

    public List<String> getTodos() {
        return todos;
    }

    public List<String> getCompleted() {
        return completed;
    }

    public String getTodo(int index) {
        return todos.get(index);
    }
}
