package me.todoReminder.bot.core.aesthetics;

import net.dv8tion.jda.api.EmbedBuilder;
import java.time.Instant;

public final class EmbedReplies{
    public static EmbedBuilder errorEmbed() {
        return new EmbedBuilder()
                .setTitle(Emojis.error+" Error")
                .setColor(Colors.error)
                .setFooter("Use t.help for assistance")
                .setTimestamp(Instant.now());
    }

    public static EmbedBuilder commandErrorEmbed() {
        return new EmbedBuilder()
                .setColor(Colors.error)
                .setTitle(Emojis.error+" Command Error")
                .setDescription("An error occurred in the execution of this command.\nDevelopers are investigating the issue.")
                .setTimestamp(Instant.now());
    }

    public static EmbedBuilder onCooldownEmbed(long milliseconds) {
        return new EmbedBuilder()
                .setColor(Colors.error)
                .setTitle(Emojis.error+" Slow it down sir")
                .setDescription("Wait `"+milliseconds/1000.0+"` more seconds before using another command please.");
    }

    public static EmbedBuilder infoEmbed(boolean success) {
        return new EmbedBuilder()
                .setColor(success ? Colors.success : Colors.info);
    }
}
