package me.todoReminder.bot.core.database.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Entity("users")
public class UserModel {
   @Id
   private ObjectId id;
   private String userID;
   private String prefix;
   @Reference
    private List<TodoList> todoLists;
   @Reference
    private List<Reminder> reminders;

    public UserModel() { }
    public UserModel(String userID, String prefix) {
        id = new ObjectId();
        this.userID = userID;
        this.prefix = prefix;
        todoLists = new ArrayList<>();
        reminders = new ArrayList<>();
    }

    public ObjectId getId() {
        return id;
    }

    public String getUserID() {
        return userID;
    }

    public String getPrefix() {
        return prefix;
    }

    public List<TodoList> getTodoLists() {
        return todoLists;
    }

    public List<Reminder> getReminders() {
        return reminders;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setTodoLists(List<TodoList> todoLists) {
        this.todoLists = todoLists;
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }
}
