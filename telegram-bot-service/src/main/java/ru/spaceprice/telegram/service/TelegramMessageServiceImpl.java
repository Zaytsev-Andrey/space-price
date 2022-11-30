package ru.spaceprice.telegram.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.spaceprice.dto.ProductDto;
import ru.spaceprice.telegram.property.BotCardSliderProperty;

@Service
@Log4j2
@RequiredArgsConstructor
public class TelegramMessageServiceImpl implements TelegramMessageService {

    private final BotCardSliderProperty botCardSliderProperty;

    private final ProductSearchService productSearchService;

    private final ProductCardSliderService productCardSliderService;

    private final ProductSubscriptionService productSubscriptionService;

    @Override
    public void messageRequest(Message message) {
        String chatId = message.getChatId().toString();
        log.info("New search request from chatId: '{}'", chatId);

        // Create new product card slider
        productCardSliderService.create(chatId);

        // find products
        productSearchService.findProducts(message.getText())
                .subscribe(new Subscriber<>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        subscription.request(botCardSliderProperty.getCapacity());
                        productSubscriptionService.registrationSubscription(chatId, subscription);
                    }

                    @Override
                    public void onNext(ProductDto productDto) {
                        productCardSliderService.addProduct(chatId, productDto);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        log.info("Search error {}", throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        productSubscriptionService.closeSubscription(chatId);
                    }
                });
//                .subscribe(productDto -> addProductToCardSlider(productCardSlider, productDto));
    }

}
