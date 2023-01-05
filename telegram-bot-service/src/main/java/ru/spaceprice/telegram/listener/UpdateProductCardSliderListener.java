package ru.spaceprice.telegram.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.spaceprice.telegram.sender.BotSender;
import ru.spaceprice.telegram.service.InformationMessageService;
import ru.spaceprice.telegram.service.ProductCardSliderService;
import ru.spaceprice.telegram.storage.entity.ProductCardSlider;

@Component
@RequiredArgsConstructor
@Log4j2
public class UpdateProductCardSliderListener {

    private final BotSender botSender;

    private final ProductCardSliderService productCardSliderService;

    private final InformationMessageService informationMessageService;

    @EventListener
    public void addingProductToCardSlider(ProductCardSlider productCardSlider) {
        log.info("Product card slider changed for message id='{}'", productCardSlider.getMessageId());

        String chatId = productCardSlider.getId();
        if (productCardSliderService.getSize(chatId) == 0) {
            informationMessageService.sendEmptySearchMessage(chatId);
            return;
        }

        botSender.sendProductCardSlider(productCardSlider)
                .ifPresent(message -> productCardSliderService.update(
                                productCardSlider.getId(),
                                p -> {
                                    if (p.getMessageId() == null) {
                                        p.setMessageId(message.getMessageId());
                                    }
                                }
                        )
                );
    }

}
