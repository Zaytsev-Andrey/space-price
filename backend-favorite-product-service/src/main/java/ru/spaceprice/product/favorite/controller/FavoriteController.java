package ru.spaceprice.product.favorite.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.spaceprice.dto.ProductDto;
import ru.spaceprice.product.favorite.service.FavoriteService;

@RestController
@RequestMapping("favorite")
@Api(tags = "Favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @ApiOperation(value = "This method is used to get favorite products of user")
    @PreAuthorize("hasAuthority('SCOPE_favorite-product')")
    @GetMapping("/{userId}")
    public Flux<ProductDto> getFavoriteProducts(@PathVariable("userId") String userId) {
        return favoriteService.findUserProducts(userId);
    }

    @ApiOperation(value = "This method is used to create favorite of user")
    @PreAuthorize("hasAuthority('SCOPE_favorite_product')")
    @PostMapping
    public Mono<Boolean> newFavorite(@RequestBody String userId) {
        return favoriteService.createFavorite(userId);
    }

    @ApiOperation(value = "This method is used to add product to favorite products of user")
    @PreAuthorize("hasAuthority('SCOPE_favorite_product')")
    @PostMapping("/{userId}/add/product")
    public Flux<ProductDto> addProductToFavorite(@PathVariable("userId") String userId,
                                                 @RequestBody ProductDto productDto) {
        return favoriteService.addProductToUserFavorites(userId, productDto);
    }

    @ApiOperation(value = "This method is used to delete product from favorite products of user")
    @PreAuthorize("hasAuthority('SCOPE_favorite_product')")
    @DeleteMapping("/{userId}/del/product")
    public Flux<ProductDto> deleteProductFromFavorite(@PathVariable("userId") String userId,
                                                      @RequestBody ProductDto productDto) {
        return favoriteService.deleteProductFromUserFavorite(userId, productDto);
    }
}
