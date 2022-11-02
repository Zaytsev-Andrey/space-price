package ru.spaceprice.telegram.chat.keyboard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.spaceprice.telegram.property.BotChatFormatProperty;
import ru.spaceprice.telegram.storage.entity.ProductCardSlider;
import ru.spaceprice.telegram.chat.command.CardButtonCommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SpacePriceNavigationButton implements ChatButton {

    private final BotChatFormatProperty chatFormatProperty;

    private InlineKeyboardButton getButton(String text, CardButtonCommand callbackCommand) {
        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(callbackCommand.name())
                .build();
    }

    private InlineKeyboardButton getPreviousButton() {
        return getButton(chatFormatProperty.getButtonPrevious(), CardButtonCommand.PREVIOUS);
    }

    private InlineKeyboardButton getNextButton() {
        return getButton(chatFormatProperty.getButtonNext(), CardButtonCommand.NEXT);
    }

    private InlineKeyboardButton getCurrentPositionButton(int currentIndex, int size) {
        return getButton(String.format(chatFormatProperty.getButtonPage(), currentIndex, size),
                CardButtonCommand.NO_ACTION);
    }

    @Override
    public List<InlineKeyboardButton> createButtons(ProductCardSlider productCardSlider) {
        // If Card Slider disable then return empty InlineKeyboardMarkup
        if (!productCardSlider.isActive()) {
            return Collections.emptyList();
        }

        List<InlineKeyboardButton> navigationButtonRow = new ArrayList<>();
        if (hasPrevious(productCardSlider)) {
            navigationButtonRow.add(getPreviousButton());
        }
        navigationButtonRow.add(getCurrentPositionButton(productCardSlider.getCurrentPosition() + 1,
                getCardSliderSize(productCardSlider)));
        if (hasNext(productCardSlider)) {
            navigationButtonRow.add(getNextButton());
        }

        return navigationButtonRow;
    }

    private boolean hasNext(ProductCardSlider productCardSlider) {
        return productCardSlider.getCurrentPosition() < getCardSliderSize(productCardSlider) - 1;
    }

    private boolean hasPrevious(ProductCardSlider productCardSlider) {
        return productCardSlider.getCurrentPosition() > 0;
    }

    private int getCardSliderSize(ProductCardSlider productCardSlider) {
        return productCardSlider.getProducts().size();
    }
}
