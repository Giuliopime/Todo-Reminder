package me.todoReminder.bot;

import me.todoReminder.bot.core.CommandHandler;
import me.todoReminder.bot.core.EventManager;
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
            log.error("Could not complete Main Thread routine!", e);
            log.error("Cannot continue! Exiting program...");
            System.exit(1);
        }
    }

    private Bot() throws Exception {
        JDA api = null;
        try {
            api = JDABuilder
                    .createDefault(Config.get("TOKEN"))
                    .setActivity(Activity.watching("your tasks"))
                    .addEventListeners(new EventManager())
                    .build()
                    .awaitReady();

            CommandHandler.registerCommands();
        } catch (LoginException e) {
            log.info("Invalid Token for ToDo Reminder!");
        } catch (InterruptedException e) {
            log.info("ToDo Reminder thread got interrupted while booting up");
        }
    }
}
