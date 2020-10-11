package me.todoReminder.bot.core.database.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import org.bson.types.ObjectId;

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

   public String getPrefix() {
       return prefix;
   }

    public List<TodoList> getTodoLists() {
        return todoLists;
    }
}
