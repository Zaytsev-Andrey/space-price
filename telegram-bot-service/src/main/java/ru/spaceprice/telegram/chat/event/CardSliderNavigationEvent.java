package ru.spaceprice.telegram.chat.event;

import lombok.Getter;
import ru.spaceprice.telegram.storage.entity.ProductCardSlider;

@Getter
public class CardSliderNavigationEvent {

    private final String id;

    private final int position;

    private final int size;

    private CardSliderNavigationEvent(String id, int position, int size) {
        this.id = id;
        this.position = position;
        this.size = size;
    }

    public static CardSliderNavigationEvent fromProductCardSlider(ProductCardSlider productCardSlider) {
        return new CardSliderNavigationEvent(
                productCardSlider.getId(),
                productCardSlider.getCurrentPosition(),
                productCardSlider.getProducts().size()
        );
    }
}
