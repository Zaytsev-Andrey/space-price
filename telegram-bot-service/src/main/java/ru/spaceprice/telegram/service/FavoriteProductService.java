package ru.spaceprice.telegram.service;

import ru.spaceprice.dto.ProductDto;

public interface FavoriteProductService {

    void createFavorite(String chatId);

    boolean existProductInFavorite(String chatId, ProductDto productDto);

    void getUserFavorite(String chatId);

    void addToFavorite(String chatId);

    void deleteFromFavorite(String chatId);
}
