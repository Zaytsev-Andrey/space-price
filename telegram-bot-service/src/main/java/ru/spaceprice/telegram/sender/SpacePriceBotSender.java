package ru.spaceprice.telegram.sender;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.spaceprice.dto.ProductDto;
import ru.spaceprice.telegram.SpacePriceBot;
import ru.spaceprice.telegram.storage.entity.ProductCardSlider;
import ru.spaceprice.telegram.chat.keyboard.ChatKeyboard;
import ru.spaceprice.telegram.chat.notification.NotificationFormatter;
import ru.spaceprice.telegram.chat.slider.CardSliderFormatter;

import java.util.Optional;

@Component
@Log4j2
@RequiredArgsConstructor
public class SpacePriceBotSender implements BotSender {

    private final SpacePriceBot spacePriceBot;

    private final CardSliderFormatter cardSliderCaptionFormatter;

    private final NotificationFormatter notificationFormatter;

    private final ChatKeyboard chatKeyboard;

    @Override
    public Optional<Message> sendProductCardSlider(ProductCardSlider productCardSlider) {
        if (productCardSlider.getMessageId() == null) {
            return Optional.ofNullable(sendNewProductCardSlider(productCardSlider));
        } else {
            updateProductCardSlider(productCardSlider);
            return Optional.empty();
        }
    }

    @Override
    public void sendInformationMessage(String chatId, String messageText) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(messageText)
                .parseMode(ParseMode.HTML)
                .build();

        sendMessage(sendMessage);
    }

    @Override
    public void sendPriceNotification(String chatId, ProductDto productDto) {
        sendPhoto(
                SendPhoto.builder()
                        .chatId(chatId)
                        .photo(new InputFile(productDto.getImageLink()))
                        .caption(notificationFormatter.format(productDto))
                        .parseMode(ParseMode.HTML)
                        .build()
        );
    }

    private void updateProductCardSlider(ProductCardSlider productCardSlider) {
        ProductDto currentProducts = productCardSlider.navigator().getCurrentProduct();
        EditMessageMedia messageMedia = EditMessageMedia.builder()
                .chatId(productCardSlider.getId())
                .messageId(productCardSlider.getMessageId())
                .media(InputMediaPhoto.builder()
                        .media(currentProducts.getImageLink())
                        .caption(cardSliderCaptionFormatter.format(productCardSlider))
                        .parseMode(ParseMode.HTML)
                        .build())
                .replyMarkup(chatKeyboard.createKeyboard(productCardSlider))
                .build();

        updateMessageMedia(messageMedia);
    }

    private Message sendNewProductCardSlider(ProductCardSlider productCardSlider) {
        ProductDto currentProducts = productCardSlider.navigator().getCurrentProduct();
        SendPhoto photo = SendPhoto.builder()
                .chatId(productCardSlider.getId())
                .photo(new InputFile(currentProducts.getImageLink()))
                .caption(cardSliderCaptionFormatter.format(productCardSlider))
                .replyMarkup(chatKeyboard.createKeyboard(productCardSlider))
                .parseMode(ParseMode.HTML)
                .build();

        return sendPhoto(photo);
    }

    private synchronized Message sendPhoto(SendPhoto photo) {
        try {
            return spacePriceBot.execute(photo);
        } catch (TelegramApiException e) {
            log.error("Photo was not sent", e);
        }

        return null;
    }

    private synchronized void updateMessageMedia(EditMessageMedia messageMedia) {
        try {
            spacePriceBot.execute(messageMedia);
        } catch (TelegramApiException e) {
            log.error("Message media was not updated", e);
        }
    }

    private synchronized void sendMessage(SendMessage sendMessage) {
        try {
            spacePriceBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Message was not sent", e);
        }
    }
}
