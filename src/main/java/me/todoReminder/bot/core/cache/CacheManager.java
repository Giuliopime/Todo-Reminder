package me.todoReminder.bot.core.cache;

import com.google.gson.Gson;
import me.todoReminder.bot.core.database.DatabaseManager;
import me.todoReminder.bot.core.database.schemas.GuildSchema;
import me.todoReminder.bot.core.database.schemas.ReminderSchema;
import me.todoReminder.bot.core.database.schemas.UserSchema;
import redis.clients.jedis.Jedis;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CacheManager {
    private static CacheManager instance;
    private final Jedis jedis;
    private final Gson gson;

    private CacheManager() {
        jedis = new Jedis("localhost", 6379);
        gson = new Gson();
    }

    public static CacheManager getInstance() {
        if(instance == null) {
            instance = new CacheManager();
        }
        return instance;
    }

    public void shutdown() {
        jedis.shutdown();
    }

    // User Commands Cool down
    public long isOnCooldown(String userID) {
        long currentTime = System.currentTimeMillis();
        String cooldown = jedis.hget("cooldowns", userID);

        if(cooldown != null) {
            long timestamp = Long.parseLong(cooldown);

            if(currentTime - timestamp <= 750) return 750 - (currentTime - timestamp);
            jedis.hset("cooldowns", userID, Long.toString(currentTime));
            return 0;
        }
        jedis.hset("cooldowns", userID, Long.toString(currentTime));
        return 0;
    }

    // Get stuff from the cache
    public UserSchema getUser(String userID) {
        String json = jedis.get(userID);
        if(json == null) return null;
        return gson.fromJson(json, UserSchema.class);
    }

    public GuildSchema getGuild(String guildID) {
        String json = jedis.get(guildID);
        if(json == null) return null;
        return gson.fromJson(json, GuildSchema.class);
    }

    // Cache stuff
    public void cacheGuild(GuildSchema guildSchema) {
        jedis.set(guildSchema.getGuildID(), gson.toJson(guildSchema));
    }

    public void cacheUser(UserSchema userSchema) {
        jedis.set(userSchema.getUserID(), gson.toJson(userSchema));
    }

    // Invalidate cache
    public void invalidateData(String ID) {
        jedis.del(ID);
    }

    // Reminders
    public List<ReminderSchema> getAllReminders() {
        Map<String, String> reminders = jedis.hgetAll("reminders");
        if(reminders == null || reminders.isEmpty()) return new ArrayList<>();

        List<ReminderSchema> reminderSchemas = new ArrayList<>();
        for(String reminderSchemaJSON: reminders.values())
            reminderSchemas.add(gson.fromJson(reminderSchemaJSON, ReminderSchema.class));

        return reminderSchemas;
    }

    public void setAllReminders() {
        List<ReminderSchema> reminders = DatabaseManager.getInstance().getAllReminders();
        for(ReminderSchema reminderSchema: reminders)
            jedis.hset("reminders", reminderSchema.getId(), gson.toJson(reminderSchema));
    }

    public void addReminder(ReminderSchema reminderSchema) {
        jedis.hset("reminders", reminderSchema.getId(), gson.toJson(reminderSchema));
    }

    public void removeReminder(String reminderID) {
        jedis.hdel("reminders", reminderID);
    }
}
