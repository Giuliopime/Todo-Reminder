package me.todoReminder.bot;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import me.todoReminder.bot.core.commands.CommandHandler;
import me.todoReminder.bot.events.EventManager;
import me.todoReminder.bot.core.database.DatabaseManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;


public class Bot {
    private static final Logger log = LoggerFactory.getLogger(Bot.class);

    public static void main(String[] args) {
        try {
            new Bot();
        } catch (Exception e) {
            shutdown(null, "Could not complete Main Thread routine!", e);
        }
    }

    private Bot() {
        JDA jda = null;
        try {
            // Event waiter is used for Message Collectors in MessageReceived.class
            EventWaiter waiter = new EventWaiter();
            jda = JDABuilder
                    .createDefault(Config.get("TOKEN"))
                    .setActivity(Activity.watching("your tasks | t.help"))
                    .addEventListeners(new EventManager(waiter), waiter)
                    .build()
                    .awaitReady();

            // Registers all the bot commands
            CommandHandler.registerCommands();

            // Init the connection to the database
            DatabaseManager.getInstance();
        } catch (LoginException e) {
            shutdown(null, "Invalid Token for ToDo Reminder!", e);
        } catch (InterruptedException e) {
            shutdown(null, "ToDo Reminder thread got interrupted while booting up!", e);
        } catch (IllegalArgumentException e) {
            shutdown(jda, "Error while registering the bot commands!", e);
        }
    }

    public static void shutdown(JDA jda, String reason, Exception e) {
        log.info("Shutting down.\nReason: {}", reason);
        if(e != null) log.info("Error: ", e);

        if(jda != null) jda.shutdown();

        DatabaseManager.getInstance().shutdown();
        System.exit(0);
    }
}
