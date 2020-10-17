package me.todoReminder.bot.core.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.mapping.MapperOptions;
import dev.morphia.query.Query;
import dev.morphia.query.experimental.filters.Filters;
import dev.morphia.query.experimental.updates.UpdateOperators;
import me.todoReminder.bot.core.cache.CacheManager;
import me.todoReminder.bot.core.commands.CommandContext;
import me.todoReminder.bot.core.database.schemas.GuildSchema;
import me.todoReminder.bot.core.database.schemas.TodoList;
import me.todoReminder.bot.core.database.schemas.UserSchema;

import java.util.Collections;


public class DatabaseManager {
    private static DatabaseManager instance;
    private final Datastore datastore;
    private final MongoClient mongoClient;


    private DatabaseManager() {
        mongoClient = MongoClients.create();
        datastore = Morphia.createDatastore(mongoClient, "todoReminderDB", MapperOptions.builder().storeEmpties(true).build());
        datastore.getMapper().mapPackage("me.todoReminder.bot.core.database.models");
        datastore.ensureIndexes();
    }

    public static DatabaseManager getInstance() {
        if(instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public void shutdown() {
        mongoClient.close();
    }

    // Getters for the database
    public UserSchema getUser(String userID) {
        UserSchema userSchema = CacheManager.getInstance().getUser(userID);
        if(userSchema != null) return userSchema;

        Query<UserSchema> query = datastore.find(UserSchema.class)
                .filter(Filters.eq("userID", userID));

        if(query.first() != null) {
            CacheManager.getInstance().cacheUser(query.first());
            return query.first();
        }
        else {
            userSchema = new UserSchema(userID);
            datastore.save(userSchema);
            CacheManager.getInstance().cacheUser(userSchema);
            return userSchema;
        }
    }

    public String getPrefix(String guildID) {
        GuildSchema guildSchema = CacheManager.getInstance().getGuild(guildID);
        if(guildSchema != null) return guildSchema.getPrefix();

        Query<GuildSchema> query = datastore.find(GuildSchema.class)
                .filter(Filters.eq("guildID", guildID));

        if(query.first() != null) {
            CacheManager.getInstance().cacheGuild(query.first());
            return query.first().getPrefix();
        }
        else {
            guildSchema = new GuildSchema(guildID, "t.");
            datastore.save(guildSchema);
            CacheManager.getInstance().cacheGuild(guildSchema);
            return guildSchema.getPrefix();
        }
    }


    // Update database data
    public void setPrefix(String guildID, String prefix) {
        CacheManager.getInstance().invalidateData(guildID);
        datastore.find(GuildSchema.class)
                .filter(Filters.eq("guildID", guildID))
                .update(UpdateOperators.set("prefix", prefix))
                .execute();
    }

    public void newList(String userID, String name) {
        CacheManager.getInstance().invalidateData(userID);
        datastore.find(UserSchema.class)
                .filter(Filters.eq("userID", userID))
                .update(UpdateOperators.push("todoLists", new TodoList(name)))
                .execute();
    }

    public void deleteList(String userID, CommandContext ctx) {
        CacheManager.getInstance().invalidateData(userID);
        datastore.find(UserSchema.class)
                .filter(Filters.eq("userID", userID))
                .update(UpdateOperators.pullAll("todoLists", Collections.singletonList(ctx.getSelectedList())))
                .execute();
    }

    public void addTodo(String userID, int listIndex, String todo) {
        CacheManager.getInstance().invalidateData(userID);
        datastore.find(UserSchema.class)
                .filter(Filters.eq("userID", userID))
                .update(UpdateOperators.push("todoLists."+listIndex+".todos", todo))
                .execute();
    }

    public void removeTodo(String userID, int listIndex, String todo) {
        CacheManager.getInstance().invalidateData(userID);
        datastore.find(UserSchema.class)
                .filter(Filters.eq("userID", userID))
                .modify(UpdateOperators.pullAll("todoLists."+listIndex+".todos", Collections.singletonList(todo)))
                .execute();
    }

    public void completeTodo(String userID, int listIndex, String todo) {
        CacheManager.getInstance().invalidateData(userID);
        datastore.find(UserSchema.class)
                .filter(Filters.eq("userID", userID))
                .modify(UpdateOperators.pullAll("todoLists."+listIndex+".todos", Collections.singletonList(todo)), UpdateOperators.push("todoLists."+listIndex+".completed", todo))
                .execute();
    }
}