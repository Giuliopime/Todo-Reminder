package me.todoReminder.bot.core.database;

import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class DatabaseManager {
    private static final Logger log = LoggerFactory.getLogger(DatabaseManager.class);
    private static DatabaseManager instance;
    private MongoClient mongoClient;
    private DB db;
    private DBCollection users;

    private DatabaseManager() {}

    public static DatabaseManager getInstance() {
        if(instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public void init() throws UnknownHostException {
        MongoClient mongoClient = new MongoClient();
        db = mongoClient.getDB("todoReminderDB");
        users = db.getCollection("users");
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public void close() {
        if(mongoClient != null) mongoClient.close();
    }

    public DBObject getUser(String userID) {
        DBObject query = new BasicDBObject("userID", userID);
        DBObject user = users.find(query).one();

        if(user == null) {
            List<BasicDBObject> lists = Collections.emptyList();
            List<BasicDBObject> reminders = Collections.emptyList();
            user = new BasicDBObject("userID", userID)
                    .append("prefix", "t.")
                    .append("lists", lists)
                    .append("reminders", reminders);
            users.insert(user);
        }
        return user;
    }
}