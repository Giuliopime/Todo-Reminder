package me.todoReminder.bot.core;

import io.github.classgraph.ClassGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private static final Logger log = LoggerFactory.getLogger(CommandHandler.class);
    private static final ClassGraph CLASS_GRAPH = new ClassGraph().whitelistPackages("me.todoReminder.bot.commands");
    private static Map<String, Command> COMMANDS = new HashMap<>();

    public static void registerCommands() {
        try(var result = CLASS_GRAPH.scan()) {
            // cls stands for class
            for (var cls : result.getAllClasses()) {
                var instance = (Command) cls.loadClass().getDeclaredConstructor().newInstance();
                COMMANDS.put(instance.getName(), instance);
            }
        } catch (Exception e) {
            log.info("Couldn't register the bot commands!", e);
        }
    }

    public Command getCommand(String input) {
        if(input.startsWith("t.")) {
            String[] args = input.replace("t." , "").split("/ +/");
            Command command = COMMANDS.;
            COMMANDS.forEach((name, cmd) -> {
                if(name.equals(args[0].toLowerCase()) || Arrays.asList(cmd.getAliases()).contains(args[0].toLowerCase()))
                    command = cmd;
            });
            return command;
        }
        return null;
    }

    public Map<String, Command> getCOMMANDS() {
        return COMMANDS;
    }
}
