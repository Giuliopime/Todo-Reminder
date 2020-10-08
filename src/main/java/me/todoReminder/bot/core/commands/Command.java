package me.todoReminder.bot.core.commands;

public abstract class Command {
    public final String name, description, usage;
    private final CommandCategory category;
    private final boolean requiresArgs;
    private final String[] aliases;

    public Command(String name, String description, String usage, CommandCategory category, boolean requiresArgs, String[] aliases) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.category = category;
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

    public CommandCategory getCategory() {
        return category;
    }

    public boolean requiresArgs() {
        return requiresArgs;
    }

    public String[] getAliases() {
        return aliases;
    }
}
