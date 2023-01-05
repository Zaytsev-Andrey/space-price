package ru.spaceprice.telegram.handler.callback;

import ru.spaceprice.telegram.chat.command.CardButtonCommand;

public interface CallbackHandler {

    boolean canHandle(CardButtonCommand command);

    void handle(String chatId);
}
