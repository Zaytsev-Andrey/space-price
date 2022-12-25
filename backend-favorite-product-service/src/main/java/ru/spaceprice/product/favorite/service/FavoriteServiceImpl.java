package ru.spaceprice.product.favorite.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.spaceprice.product.favorite.document.Favorite;
import ru.spaceprice.product.favorite.repository.FavoriteRepository;
import ru.spaceprice.dto.FavoriteDto;
import ru.spaceprice.dto.ProductDto;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;

    private final ProductService productService;

    private final ModelMapper modelMapper;

    @Override
    public Flux<ProductDto> findUserProducts(String userId) {
        return favoriteRepository.findByUserId(userId)
                .map(Favorite::getProductIds)
                .flatMap(productService::findByIds)
                .map(p -> modelMapper.map(p, ProductDto.class));
    }

    @Override
    public Mono<FavoriteDto> saveFavorite(Mono<FavoriteDto> favoriteDto) {
        return favoriteDto
                .map(fd -> modelMapper.map(fd, Favorite.class))
                .flatMap(favoriteRepository::save)
                .map(f -> modelMapper.map(f, FavoriteDto.class));
    }

    @Override
    public Flux<ProductDto> addProductToUserFavorites(String userId, Mono<ProductDto> productDto) {
        return productService
                .saveProduct(productDto)
                .flatMapMany(product -> favoriteRepository
                        .findByUserId(userId)
                        .defaultIfEmpty(new Favorite(userId))
                        .doOnNext(f -> f.addProduct(product.getId()))
                        .flatMap(favoriteRepository::save)
                        .map(Favorite::getProductIds)
                        .flatMap(productService::findByIds)
                        .map(p -> modelMapper.map(p, ProductDto.class)));
    }

}
