package ru.spaceprice.product.favorite.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.spaceprice.dto.ProductDto;

public interface FavoriteService {

    Flux<ProductDto> findUserProducts(String userId);

    Mono<Boolean> createFavorite(String userId);

    Flux<ProductDto> addProductToUserFavorites(String userId, ProductDto productDto);

    Flux<ProductDto> deleteProductFromUserFavorite(String userId, ProductDto productDto);
}
