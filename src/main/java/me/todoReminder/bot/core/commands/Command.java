package me.todoReminder.bot.core.commands;

public abstract class Command {
    private final String name, description, usage;
    private final CommandCategory category;
    private final boolean requiresArgs;
    private final String[] aliases;
    private final boolean chooseList;

    public Command(String name, String description, String usage, CommandCategory category, boolean requiresArgs, String[] aliases, boolean chooseList) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.category = category;
        this.requiresArgs = requiresArgs;
        this.aliases = aliases;
        this.chooseList = chooseList;
    }

    protected abstract void run(CommandContext ctx);

    // Getters
    public String getUsageExample(String prefix) {
        return "`"+ prefix + name + (usage != null ? " " + usage : "")+"`";
    }

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

    public boolean requiresListChoice() {
        return chooseList;
    }
}
