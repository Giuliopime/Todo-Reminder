package me.todoReminder.bot.core.aesthetics;

import net.dv8tion.jda.api.EmbedBuilder;
import java.time.Instant;

public interface EmbedReplies extends Colors{
    static EmbedBuilder errorEmbed() {
        return new EmbedBuilder()
                .setTitle("❗️ Error ❗️")
                .setColor(Colors.error)
                .setFooter("Use t.help for assistance")
                .setTimestamp(Instant.now());
    }

    static EmbedBuilder warningEmbed() {
        return new EmbedBuilder()
                .setTitle("⚠️ Warning ⚠️")
                .setColor(Colors.warning)
                .setFooter("Use t.help for assistance")
                .setTimestamp(Instant.now());
    }
}
