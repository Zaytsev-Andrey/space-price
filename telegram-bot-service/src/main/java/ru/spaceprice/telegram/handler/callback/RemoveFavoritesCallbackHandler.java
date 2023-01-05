package ru.spaceprice.telegram.handler.callback;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.spaceprice.telegram.chat.command.CardButtonCommand;
import ru.spaceprice.telegram.service.FavoriteProductService;

@Component
@RequiredArgsConstructor
public class RemoveFavoritesCallbackHandler implements CallbackHandler {

    private final FavoriteProductService favoriteProductService;

    @Override
    public boolean canHandle(CardButtonCommand command) {
        return CardButtonCommand.REMOVE_FAVORITES.equals(command);
    }

    @Override
    public void handle(String chatId) {
        favoriteProductService.deleteFromFavorite(chatId);
    }
}
