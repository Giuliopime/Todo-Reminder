package me.todoReminder.bot.core.database;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;


public class DatabaseManager {
    private static final Logger log = LoggerFactory.getLogger(DatabaseManager.class);
    private static DatabaseManager instance;
    private MongoClient mongoClient;
    private DB db;
    private DBCollection usersC;

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
        usersC = db.getCollection("users");
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public void close() {
        if(mongoClient != null) mongoClient.close();
    }
}
