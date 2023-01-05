package ru.spaceprice.telegram.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.spaceprice.telegram.chat.command.BotCommand;
import ru.spaceprice.telegram.chat.command.CardButtonCommand;
import ru.spaceprice.telegram.handler.callback.CallbackHandler;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class TelegramCallbackServiceImpl implements TelegramCallbackService {

    private final List<CallbackHandler> handlers;

    @Override
    public void callbackRequest(CallbackQuery callbackQuery) {
        String chatId = callbackQuery.getFrom().getId().toString();
        CardButtonCommand command = CardButtonCommand.valueOf(callbackQuery.getData());

        handlers.stream()
                .filter(callbackHandler -> callbackHandler.canHandle(command))
                .findFirst()
                .ifPresent(callbackHandler -> callbackHandler.handle(chatId));
    }

}
