package ru.spaceprice.product.favorite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.spaceprice.dto.FavoriteDto;
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
    public Mono<FavoriteDto> newFavorite(@RequestBody Mono<FavoriteDto> favoriteDto) {
        return favoriteService.saveFavorite(favoriteDto);
    }

    @PostMapping("/{userId}/add/product")
    public Flux<ProductDto> addProductToFavorite(@PathVariable("userId") String userId,
                                                 @RequestBody Mono<ProductDto> productDto) {
        return favoriteService.addProductToUserFavorites(userId, productDto);
    }
}
