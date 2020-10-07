package me.todoReminder.bot.core.commands;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private static final Logger log = LoggerFactory.getLogger(CommandHandler.class);
    private static final ClassGraph CLASS_GRAPH = new ClassGraph().whitelistPackages("me.todoReminder.bot.commands");
    private static Map<String, Command> COMMANDS = new HashMap<>();
    private static Map<String, String> ALIASES = new HashMap<>();

    public static void registerCommands() {
        try(ScanResult result = CLASS_GRAPH.scan()) {
            // Loop trough all the classes of the .commands package (cls stands for class)
            for (ClassInfo cls : result.getAllClasses()) {
                // Get the instance of the class of the command
                Command command = (Command) cls.loadClass().getDeclaredConstructor().newInstance();

                // Add the command to the COMMAND map if absent, if not throw an exeption
                if(COMMANDS.putIfAbsent(command.getName(), command) != null)
                    throw new IllegalArgumentException("Duplicate command " + command.getName());

                // Register the aliases of the commands in the ALIASES map, throws an expection if the alias is already present in the map
                if(command.getAliases() != null) {
                    for(String alias: command.getAliases())
                        if(ALIASES.putIfAbsent(alias, command.getName()) != null)
                            throw new IllegalArgumentException("Duplicate alias " + alias);
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Bot commands loading error:\n" + e);
        }
    }

    public static boolean isCommand(String commandName) {
        Command command = COMMANDS.get(commandName.toLowerCase());
        if(command == null) {
            String alias = ALIASES.get(commandName.toLowerCase());
            if(alias != null) command = COMMANDS.get(alias);
        }
        return command != null;
    }


    public static void runCommand(CommandContext ctx) {
        String commandName = ctx.getCommandName();
        Command command = COMMANDS.get(commandName);
        if(command == null) {
            String alias = ALIASES.get(commandName.toLowerCase());
            if(alias != null) command = COMMANDS.get(alias);
        }
        if(command != null) {
            command.run(ctx);
        }
    }

    public static Map<String, Command> getCommands() {
        return COMMANDS;
    }
}
