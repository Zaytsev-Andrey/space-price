package ru.spaceprice.product.favorite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.spaceprice.dto.ProductDto;
import ru.spaceprice.product.favorite.service.FavoriteService;

@RestController
@RequestMapping("favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping("/{userId}")
    public Flux<ProductDto> getFavoriteProducts(@PathVariable("userId") String userId) {
        return favoriteService.findUserProducts(userId);
    }

    @PostMapping
    public Mono<Boolean> newFavorite(@RequestBody String userId) {
        return favoriteService.createFavorite(userId);
    }

    @PostMapping("/{userId}/add/product")
    public Flux<ProductDto> addProductToFavorite(@PathVariable("userId") String userId,
                                                 @RequestBody ProductDto productDto) {
        return favoriteService.addProductToUserFavorites(userId, productDto);
    }

    @DeleteMapping("/{userId}/del/product")
    public Flux<ProductDto> deleteProductFromFavorite(@PathVariable("userId") String userId,
                                                      @RequestBody ProductDto productDto) {
        return favoriteService.deleteProductFromUserFavorite(userId, productDto);
    }
}
