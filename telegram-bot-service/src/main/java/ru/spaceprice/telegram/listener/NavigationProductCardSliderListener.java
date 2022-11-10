package ru.spaceprice.telegram.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.spaceprice.telegram.chat.slider.CardSliderLiveEvent;
import ru.spaceprice.telegram.chat.event.CardSliderNavigationEvent;
import ru.spaceprice.telegram.property.BotCardSliderProperty;
import ru.spaceprice.telegram.service.ProductSubscriptionService;

@Component
@RequiredArgsConstructor
@Log4j2
public class NavigationProductCardSliderListener {

    private final BotCardSliderProperty botCardSliderProperty;

    private final ProductSubscriptionService productSubscriptionService;

    @EventListener
    public void navigatingProductCardSlider(CardSliderNavigationEvent cardSliderNavigationEvent) {
        log.info("Product card slider is navigated");

        if (isNeedLoad(cardSliderNavigationEvent)) {
            productSubscriptionService.loadProducts(cardSliderNavigationEvent.getId());
        }
    }

    @EventListener
    public void liveProductCardSlider(CardSliderLiveEvent cardSliderLiveEvent) {
        if (!cardSliderLiveEvent.isAlive()) {
            productSubscriptionService.closeSubscription(cardSliderLiveEvent.getId());
        }
    }

    private boolean isNeedLoad(CardSliderNavigationEvent cardSliderNavigationEvent) {
        int currentLoadLimit = cardSliderNavigationEvent.getSize() - cardSliderNavigationEvent.getPosition() - 1;
        return currentLoadLimit <= botCardSliderProperty.getLoadLimit();
    }
}
