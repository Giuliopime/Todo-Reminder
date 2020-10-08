package me.todoReminder.bot;

import me.todoReminder.bot.core.commands.CommandHandler;
import me.todoReminder.bot.core.EventManager;
import me.todoReminder.bot.core.database.DatabaseManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.rmi.UnknownHostException;


public class Bot {
    private static final Logger log = LoggerFactory.getLogger(Bot.class);

    public static void main(String[] args) {
        try {
            new Bot();
        } catch (Exception e) {
            shutdown(null, "Could not complete Main Thread routine!", e);
        }
    }

    private Bot() throws Exception {
        JDA jda = null;
        try {
            jda = JDABuilder
                    .createDefault(Config.get("TOKEN"))
                    .setActivity(Activity.watching("your tasks"))
                    .addEventListeners(new EventManager())
                    .build()
                    .awaitReady();

            CommandHandler.registerCommands();

            DatabaseManager db = DatabaseManager.getInstance();
            db.init();
        } catch (LoginException e) {
            shutdown(null, "Invalid Token for ToDo Reminder!", e);
        } catch (InterruptedException e) {
            shutdown(null, "ToDo Reminder thread got interrupted while booting up!", e);
        } catch (IllegalArgumentException e) {
            shutdown(jda, "commands / db error!", e);
        }
    }

    public static void shutdown(JDA jda, String reason, Exception e) {
        log.info("Shutting down.\nReason: {}", reason);
        if(e != null) log.info("Error: ", e);

        if(jda != null) jda.shutdown();

        DatabaseManager.getInstance().close();
        System.exit(0);
    }
}
