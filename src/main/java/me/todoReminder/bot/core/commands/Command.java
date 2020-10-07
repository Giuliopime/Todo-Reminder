package me.todoReminder.bot.core.commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public abstract class Command {
    private final String name, description, usage;
    private final boolean requiresArgs;
    private final String[] aliases;

    public Command(String name, String description, String usage, boolean requiresArgs, String[] aliases) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.requiresArgs = requiresArgs;
        this.aliases = aliases;
    }

    protected abstract void run(CommandContext ctx);
    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public boolean requiresArgs() {
        return requiresArgs;
    }

    public String[] getAliases() {
        return aliases;
    }
}