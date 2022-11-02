package ru.spaceprice.telegram.sender;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.spaceprice.dto.ProductDto;
import ru.spaceprice.telegram.storage.entity.ProductCardSlider;

import java.util.Optional;

public interface BotSender {

    Optional<Message> sendProductCardSlider(ProductCardSlider productCardSlider);

    void sendInformationMessage(String chatId, String messageText);

    void sendPriceNotification(String chatId, ProductDto productDto);
}
