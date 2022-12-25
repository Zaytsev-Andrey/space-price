package ru.spaceprice.product.favorite.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.spaceprice.dto.FavoriteDto;
import ru.spaceprice.dto.ProductDto;

public interface FavoriteService {

    Flux<ProductDto> findUserProducts(String userId);

    Mono<FavoriteDto> saveFavorite(Mono<FavoriteDto> favoriteDto);

    Flux<ProductDto> addProductToUserFavorites(String userId, Mono<ProductDto> productDto);
}
