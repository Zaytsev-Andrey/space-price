package ru.spaceprice.telegram.chat.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.spaceprice.telegram.storage.entity.ProductCardSlider;

import java.util.List;

public interface ChatButton {

    List<InlineKeyboardButton> createButtons(ProductCardSlider productCardSlider);
}
