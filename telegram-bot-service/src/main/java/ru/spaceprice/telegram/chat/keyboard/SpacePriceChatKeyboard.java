package ru.spaceprice.telegram.chat.keyboard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.spaceprice.telegram.storage.entity.ProductCardSlider;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SpacePriceChatKeyboard implements ChatKeyboard {

    private final ChatButton spacePriceNavigationButton;

    private final ChatButton spacePriceFavoritesButton;

    @Override
    public InlineKeyboardMarkup createKeyboard(ProductCardSlider productCardSlider) {
        return InlineKeyboardMarkup.builder()
                .keyboard(List.of(
                        spacePriceNavigationButton.createButtons(productCardSlider)
//                        spacePriceFavoritesButton.createButtons(productCardSlider)
                ))
                .build();

    }
}
