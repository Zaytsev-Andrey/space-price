package ru.spaceprice.telegram.chat.command;

public enum BotCommand {

    START("/start"),
    FAVORITES("/favorites");

    private final String command;

    BotCommand(String command) {
        this.command = command;
    }

    public boolean equalsMessageCommand(String messageCommand) {
        return command.equals(messageCommand);
    }
}
