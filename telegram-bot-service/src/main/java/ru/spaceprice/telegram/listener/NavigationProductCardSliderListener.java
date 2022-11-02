package ru.spaceprice.telegram.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.spaceprice.telegram.chat.slider.CardSliderLive;
import ru.spaceprice.telegram.chat.slider.CardSliderNavigation;
import ru.spaceprice.telegram.service.ProductSubscriptionService;

@Component
@RequiredArgsConstructor
@Log4j2
public class NavigationProductCardSliderListener {

    private final ProductSubscriptionService productSubscriptionService;

    @EventListener
    public void navigatingProductCardSlider(CardSliderNavigation cardSliderNavigation) {
        log.info("Product card slider is navigated");

        if (cardSliderNavigation.isNeedLoad()) {
            productSubscriptionService.loadProducts(cardSliderNavigation.getId());
        }
    }

    @EventListener
    public void liveProductCardSlider(CardSliderLive cardSliderLive) {
        if (!cardSliderLive.isAlive()) {
            productSubscriptionService.closeSubscription(cardSliderLive.getId());
        }
    }
}
