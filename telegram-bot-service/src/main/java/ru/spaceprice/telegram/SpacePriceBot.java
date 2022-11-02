package ru.spaceprice.telegram;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.spaceprice.telegram.handler.UpdateHandler;
import ru.spaceprice.telegram.property.BotProperty;

import java.util.List;


@Log4j2
@RequiredArgsConstructor
public class SpacePriceBot extends TelegramLongPollingBot {

    private final BotProperty botProperty;

    private final List<UpdateHandler> updateHandlers;

    @Override
    public String getBotUsername() {
        return botProperty.getUsername();
    }

    @Override
    public String getBotToken() {
        return botProperty.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("Update received: {}", update);
        updateHandlers.stream()
                .filter(handler -> handler.canHandle(update))
                .forEach(handler -> handler.handle(update));
    }

}
