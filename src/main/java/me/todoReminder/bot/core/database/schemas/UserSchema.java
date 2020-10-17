package me.todoReminder.bot.core.database.schemas;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Entity("users")
public class UserSchema {
   @Id
   private ObjectId id;
   private String userID;
   private List<TodoList> todoLists;
   private List<Reminder> reminders;

    public UserSchema() { }
    public UserSchema(String userID) {
        id = new ObjectId();
        this.userID = userID;
        todoLists = new ArrayList<>();
        reminders = new ArrayList<>();
    }

    public String getUserID() {
        return userID;
    }

    public List<TodoList> getTodoLists() {
        return todoLists;
    }


}
