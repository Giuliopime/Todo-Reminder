package me.todoReminder.bot.core.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.mapping.MapperOptions;
import dev.morphia.query.Query;
import dev.morphia.query.experimental.filters.Filters;
import dev.morphia.query.experimental.updates.UpdateOperator;
import dev.morphia.query.experimental.updates.UpdateOperators;
import me.todoReminder.bot.core.database.models.Reminder;
import me.todoReminder.bot.core.database.models.TodoList;
import me.todoReminder.bot.core.database.models.UserModel;
import net.dv8tion.jda.api.entities.User;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DatabaseManager {
    private static final Logger log = LoggerFactory.getLogger(DatabaseManager.class);
    private static DatabaseManager instance;
    private Datastore datastore;
    private MongoClient mongoClient;


    private DatabaseManager() {}

    public static DatabaseManager getInstance() {
        if(instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public void init() {
        mongoClient = MongoClients.create();
        datastore = Morphia.createDatastore(mongoClient, "todoReminderDB", MapperOptions.builder().storeEmpties(true).build());
        datastore.getMapper().mapPackage("me.todoReminder.bot.core.database.models");
        datastore.ensureIndexes();
    }

    public void close() {
        mongoClient.close();
    }


    // Get
    public UserModel getUser(String userID) {
        Query<UserModel> query = datastore.find(UserModel.class)
                .filter(Filters.eq("userID", userID));

        if(query.first() != null) return query.first();
        else {
            UserModel userModel = new UserModel(userID, "t.");
            datastore.save(userModel);
            return userModel;
        }
    }

    // Update
    public void setPrefix(String userID, String prefix) {
        datastore.find(UserModel.class)
                .filter(Filters.eq("userID", userID))
                .update(UpdateOperators.set("prefix", prefix))
                .execute();
    }

    public void newList(String userID, String name) {
        datastore.find(UserModel.class)
                .filter(Filters.eq("userID", userID))
                .update(UpdateOperators.push("todoLists", new TodoList(name)))
                .execute();
    }

    public void addTodo(String userID, int listIndex, String todo) {
        datastore.find(UserModel.class)
                .filter(Filters.eq("userID", userID))
                .update(UpdateOperators.push("todoLists."+listIndex+".todos", todo))
                .execute();
    }

    public void removeTodo(String userID, int listIndex, String todo) {
        datastore.find(UserModel.class)
                .filter(Filters.eq("userID", userID))
                .update(UpdateOperators.pull("todoLists." + listIndex + ".todos", Filters.eq("value", todo)))
                .execute();
    }

    public void completeTodo(int listIndex, int todoIndex, UserModel userData) {
        String todo = userData.getTodoLists().get(listIndex).getTodo(todoIndex);

        datastore.find(UserModel.class)
                .filter(Filters.eq("userID", userData.getUserID()))
                .modify(UpdateOperators.pullAll("todoLists."+listIndex+".todos", Collections.singletonList(userData.getTodoLists().get(listIndex).getTodo(todoIndex))), UpdateOperators.push("todoLists."+listIndex+".completed", todo))
                .execute();
    }
}