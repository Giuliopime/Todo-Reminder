package me.todoReminder.bot.core.cache;

import com.google.gson.Gson;
import me.todoReminder.bot.core.database.schemas.GuildSchema;
import me.todoReminder.bot.core.database.schemas.UserSchema;
import redis.clients.jedis.Jedis;

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
}
