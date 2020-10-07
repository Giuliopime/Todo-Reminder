package me.todoReminder.bot.core;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.time.Instant;

public class EmbedReplies {
    public static EmbedBuilder errorEmbed() {
        return new EmbedBuilder()
                .setTitle("❗️ Error ❗️")
                .setColor(Color.RED)
                .setFooter("Use t.help for assistance")
                .setTimestamp(Instant.now());
    }

    public static EmbedBuilder warningEmbed() {
        return new EmbedBuilder()
                .setTitle("⚠️ Warning ⚠️")
                .setColor(Color.YELLOW)
                .setFooter("Use t.help for assistance")
                .setTimestamp(Instant.now());
    }
}
