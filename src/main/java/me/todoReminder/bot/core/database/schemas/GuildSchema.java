package me.todoReminder.bot.core.database.schemas;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

@Entity("guilds")
public class GuildSchema {
    @Id
    private ObjectId id;
    private String guildID;
    private String prefix;

    public GuildSchema() { }

    public GuildSchema(String guildID, String prefix) {
        this.guildID = guildID;
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
