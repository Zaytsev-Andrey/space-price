package ru.spaceprice.telegram.chat.slider;

import lombok.Getter;
import ru.spaceprice.telegram.storage.entity.ProductCardSlider;

@Getter
public class CardSliderLive {

    private final String id;

    private final boolean alive;

    private CardSliderLive(String id, boolean alive) {
        this.id = id;
        this.alive = alive;
    }

    public static CardSliderLive fromProductCardSlider(ProductCardSlider productCardSlider) {
        return new CardSliderLive(productCardSlider.getId(), productCardSlider.isActive());
    }
}
