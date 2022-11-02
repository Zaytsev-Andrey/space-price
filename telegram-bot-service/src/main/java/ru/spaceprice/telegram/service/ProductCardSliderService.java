package ru.spaceprice.telegram.service;

import ru.spaceprice.dto.ProductDto;

public interface ProductCardSliderService {

    void create(String chatId);

    void addProduct(String chatId, ProductDto productDto);

    void registrationMessage(String chatId, Integer messageId);

}
