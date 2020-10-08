package me.todoReminder.bot.core.database;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;


public class DatabaseManager {
    private static final Logger log = LoggerFactory.getLogger(DatabaseManager.class);
    private static DatabaseManager instance;
    private MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection<Document> users;

    private DatabaseManager() {}

    public static DatabaseManager getInstance() {
        if(instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public void init() {
        try {
            MongoClient mongoClient = new MongoClient();
            db = mongoClient.getDatabase("todoReminderDB");
            users = db.getCollection("users");
            log.info("Connection to mongoDB successful!");
        } catch (MongoTimeoutException e) {
            throw new IllegalArgumentException("Error connecting to mongoDB:\n", e);
        }
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public void close() {
        if(mongoClient != null) mongoClient.close();
    }

    public Document getUser(String userID) {
        Document user = users.find(new Document("userID", userID)).first();

        if(user == null) {
            List<BasicDBObject> lists = Collections.emptyList();
            List<BasicDBObject> reminders = Collections.emptyList();
            user = new Document("userID", userID)
                    .append("prefix", "t.")
                    .append("lists", lists)
                    .append("reminders", reminders);
            users.insertOne(user);
        }
        return user;
    }

    public void setPrefix(String userID, String prefix) {
        users.updateOne(Filters.eq("userID", userID), Updates.set("prefix", prefix));
    }
}