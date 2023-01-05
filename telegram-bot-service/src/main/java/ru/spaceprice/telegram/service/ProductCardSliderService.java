package ru.spaceprice.telegram.service;

import ru.spaceprice.dto.ProductDto;
import ru.spaceprice.telegram.storage.entity.ProductCardSlider;

import java.util.function.Consumer;

public interface ProductCardSliderService {

    void create(String chatId);

    void update(String chatId, Consumer<ProductCardSlider> consumer);

    ProductDto getCurrentProduct(String chatId);

    long getSize(String chatId);

}
