package ru.spaceprice.telegram.chat.slider;


import ru.spaceprice.telegram.storage.entity.ProductCardSlider;

public interface CardSliderFormatter {

    String format(ProductCardSlider productCardSlider);
}
