package me.todoReminder.bot.core.aesthetics;

import net.dv8tion.jda.api.EmbedBuilder;
import java.time.Instant;

public final class EmbedReplies{
    public static EmbedBuilder errorEmbed() {
        return new EmbedBuilder()
                .setTitle("Error "+Emojis.error)
                .setColor(Colors.error)
                .setFooter("Use t.help for assistance")
                .setTimestamp(Instant.now());
    }

    public static EmbedBuilder infoEmbed(boolean success) {
        return new EmbedBuilder()
                .setColor(success ? Colors.success : Colors.info);
    }

    public static EmbedBuilder commandErrorEmbed() {
        return new EmbedBuilder()
                .setColor(Colors.error)
                .setTitle("Command Error "+Emojis.error)
                .setDescription("An error occurred in the execution of this command.\nDevelopers are investigating the issue.")
                .setTimestamp(Instant.now());
    }
}
