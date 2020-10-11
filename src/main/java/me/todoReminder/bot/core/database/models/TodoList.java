package me.todoReminder.bot.core.database.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

@Entity("todoLists")
public class TodoList {
    @Id
    private ObjectId id;
    private String name;
    private List<String> todos;
    private List<String> completed;

    public TodoList() { }

    public TodoList(String name) {
        id = new ObjectId();
        this.name = name;
        todos = new ArrayList<>();
        completed = new ArrayList<>();
    }

    public ObjectId getId() {
        return id;
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

    public void setId(ObjectId id) {
        this.id = id;
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
}
