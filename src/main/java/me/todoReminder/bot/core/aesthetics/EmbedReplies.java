package me.todoReminder.bot.core.aesthetics;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.time.Instant;

public class EmbedReplies implements Colors{
    public static EmbedBuilder errorEmbed() {
        return new EmbedBuilder()
                .setTitle("❗️ Error ❗️")
                .setColor(Colors.error)
                .setFooter("Use t.help for assistance")
                .setTimestamp(Instant.now());
    }

    public static EmbedBuilder warningEmbed() {
        return new EmbedBuilder()
                .setTitle("⚠️ Warning ⚠️")
                .setColor(Colors.warning)
                .setFooter("Use t.help for assistance")
                .setTimestamp(Instant.now());
    }
}
