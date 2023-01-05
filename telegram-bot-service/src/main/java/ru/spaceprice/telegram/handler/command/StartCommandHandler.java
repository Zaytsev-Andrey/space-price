package ru.spaceprice.telegram.handler.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.spaceprice.telegram.chat.command.BotCommand;
import ru.spaceprice.telegram.service.FavoriteProductService;
import ru.spaceprice.telegram.service.InformationMessageService;

@Component
@RequiredArgsConstructor
public class StartCommandHandler implements CommandHandler {

    private final InformationMessageService informationMessageService;

    private final FavoriteProductService favoriteProductService;

    @Override
    public boolean canHandle(String messageCommand) {
        return BotCommand.START.equalsMessageCommand(messageCommand);
    }

    @Override
    public void handle(Message message) {
        String chatId = message.getChatId().toString();
        informationMessageService.sendGreetingsMessage(chatId);
        favoriteProductService.createFavorite(chatId);
    }
}
