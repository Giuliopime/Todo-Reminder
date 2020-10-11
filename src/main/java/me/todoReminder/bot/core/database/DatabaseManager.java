package me.todoReminder.bot.core.database;

import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.query.experimental.filters.Filters;
import me.todoReminder.bot.core.database.models.TodoList;
import me.todoReminder.bot.core.database.models.UserModel;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class DatabaseManager {
    private static final Logger log = LoggerFactory.getLogger(DatabaseManager.class);
    private static DatabaseManager instance;
    private Datastore datastore;


    private DatabaseManager() {}

    public static DatabaseManager getInstance() {
        if(instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public void init() {
        datastore = Morphia.createDatastore(MongoClients.create(), "todoReminderDB");
        datastore.getMapper().mapPackage("me.todoReminder.bot.core.database.models");
        datastore.ensureIndexes();
    }

    public UserModel getUser(String userID) {
        return datastore.find(UserModel.class)
                .filter(Filters.eq("userID", userID))
                .first();
    }

    public void newList(String userID, String name) {
        TodoList todoList = new TodoList(new ObjectId(), name, null, null);
        UserModel user = datastore.find(UserModel.class)
                .filter(Filters.eq("userID", userID))
                .first();

        user.getTodoLists().add(new TodoList(new ObjectId(), name, null, null));
        datastore.save(user);
    }
}