package ru.spaceprice.telegram.chat.slider;

import lombok.Getter;
import ru.spaceprice.telegram.storage.entity.ProductCardSlider;

@Getter
public class CardSliderNavigation {

    private final String id;

    private final int position;

    private final int size;

    private CardSliderNavigation(String id, int position, int size) {
        this.id = id;
        this.position = position;
        this.size = size;
    }

    public static CardSliderNavigation fromProductCardSlider(ProductCardSlider productCardSlider) {
        return new CardSliderNavigation(
                productCardSlider.getId(),
                productCardSlider.getCurrentPosition(),
                productCardSlider.getProducts().size()
        );
    }

    public boolean isNeedLoad() {
        return size - position - 1 <= 1;
    }
}
