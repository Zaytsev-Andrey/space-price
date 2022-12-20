package ru.spaceprice.telegram.chat.event;

import lombok.Getter;
import ru.spaceprice.telegram.storage.entity.ProductCardSlider;

@Getter
public class CardSliderLiveEvent {

    private final String id;

    private final boolean alive;

    private CardSliderLiveEvent(String id, boolean alive) {
        this.id = id;
        this.alive = alive;
    }

    public static CardSliderLiveEvent fromProductCardSlider(ProductCardSlider productCardSlider) {
        return new CardSliderLiveEvent(productCardSlider.getId(), productCardSlider.isActive());
    }
}
