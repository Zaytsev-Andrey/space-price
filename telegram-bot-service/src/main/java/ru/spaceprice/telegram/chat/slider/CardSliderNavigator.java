package ru.spaceprice.telegram.chat.slider;


import ru.spaceprice.dto.ProductDto;

public interface CardSliderNavigator {

    void next();

    void previous();

    boolean hasNext();

    boolean hasPrevious();

    int size();

    ProductDto getCurrentProduct();
}
