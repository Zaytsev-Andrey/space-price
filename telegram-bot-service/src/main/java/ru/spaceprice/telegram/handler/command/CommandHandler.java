package ru.spaceprice.telegram.handler.command;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface CommandHandler {

    boolean canHandle(String messageCommand);

    void handle(Message message);
}
