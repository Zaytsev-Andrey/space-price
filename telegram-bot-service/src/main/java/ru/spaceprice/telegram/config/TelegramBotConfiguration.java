package ru.spaceprice.telegram.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.spaceprice.telegram.handler.UpdateHandler;
import ru.spaceprice.telegram.property.BotProperty;
import ru.spaceprice.telegram.SpacePriceBot;

import java.util.List;

@Configuration
@ComponentScan("ru.spaceprice.message")
@RequiredArgsConstructor
public class TelegramBotConfiguration {

    private final BotProperty telegramBotProperty;

    private final List<UpdateHandler> updateHandlers;

    @Bean
    TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        return new TelegramBotsApi(DefaultBotSession.class);
    }

    @Bean
    public SpacePriceBot spacePriceBot() throws TelegramApiException {
        SpacePriceBot spacePriceBot = new SpacePriceBot(telegramBotProperty, updateHandlers);
        telegramBotsApi().registerBot(spacePriceBot);
        return spacePriceBot;
    }
}
