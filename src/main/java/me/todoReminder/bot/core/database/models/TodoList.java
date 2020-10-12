package me.todoReminder.bot.core.database.models;

import dev.morphia.annotations.Embedded;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;
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


    public String getName() {
        return name;
    }

    public List<String> getTodos() {
        return todos;
    }

    public List<String> getCompleted() {
        return completed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTodos(List<String> todos) {
        this.todos = todos;
    }

    public void setCompleted(List<String> completed) {
        this.completed = completed;
    }

    public void addTodo(String todo) {
        todos.add(todo);
    }

    public String removeTodo(int todoIndex) {
        return todos.remove(todoIndex);
    }

    public void completeTodo(int todoIndex) {
        String todo = todos.remove(todoIndex);
        completed.add(todo);
    }
}
