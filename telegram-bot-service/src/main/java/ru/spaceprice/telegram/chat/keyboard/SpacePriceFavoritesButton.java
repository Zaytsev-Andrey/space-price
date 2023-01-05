package ru.spaceprice.telegram.chat.keyboard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.spaceprice.telegram.chat.command.CardButtonCommand;
import ru.spaceprice.telegram.property.BotChatFormatProperty;
import ru.spaceprice.telegram.service.FavoriteProductService;
import ru.spaceprice.telegram.storage.entity.ProductCardSlider;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SpacePriceFavoritesButton implements ChatButton {

    private final BotChatFormatProperty botChatFormatProperty;

    private final FavoriteProductService favoriteProductService;

    @Override
    public List<InlineKeyboardButton> createButtons(ProductCardSlider productCardSlider) {
        if (!productCardSlider.isActive()) {
            return Collections.emptyList();
        }

        InlineKeyboardButton inlineKeyboardButton;
        if (favoriteProductService.existProductInFavorite(
                productCardSlider.getId(), productCardSlider.navigator().getCurrentProduct())) {
            inlineKeyboardButton =
                    getButton(botChatFormatProperty.getRemoveFavorites(), CardButtonCommand.REMOVE_FAVORITES);
        } else {
            inlineKeyboardButton =
                    getButton(botChatFormatProperty.getAddFavorites(), CardButtonCommand.ADD_FAVORITES);
        }

        return List.of(inlineKeyboardButton);
    }

    private InlineKeyboardButton getButton(String text, CardButtonCommand callbackCommand) {
        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(callbackCommand.name())
                .build();
    }
}
