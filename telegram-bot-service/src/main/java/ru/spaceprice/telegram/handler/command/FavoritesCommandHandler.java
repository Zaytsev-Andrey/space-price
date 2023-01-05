package ru.spaceprice.telegram.handler.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.spaceprice.telegram.chat.command.BotCommand;
import ru.spaceprice.telegram.service.FavoriteProductService;

@Component
@RequiredArgsConstructor
public class FavoritesCommandHandler implements CommandHandler {

    private final FavoriteProductService favoriteProductService;

    @Override
    public boolean canHandle(String messageCommand) {
        return BotCommand.FAVORITES.equalsMessageCommand(messageCommand);
    }

    @Override
    public void handle(Message message) {
        String chatId = message.getChatId().toString();
        favoriteProductService.getUserFavorite(chatId);
    }
}
