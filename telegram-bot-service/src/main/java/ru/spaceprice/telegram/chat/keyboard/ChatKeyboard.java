package ru.spaceprice.telegram.chat.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.spaceprice.telegram.storage.entity.ProductCardSlider;

public interface ChatKeyboard {

    InlineKeyboardMarkup createKeyboard(ProductCardSlider productCardSlider);
}
