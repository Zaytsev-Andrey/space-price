package ru.spaceprice.telegram.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.spaceprice.dto.ProductDto;
import ru.spaceprice.telegram.property.FavoriteProductServiceConnectionProperty;
import ru.spaceprice.telegram.storage.entity.ProductCardSlider;
import ru.spaceprice.telegram.storage.entity.UserFavorite;
import ru.spaceprice.telegram.storage.repository.UserFavoriteRepository;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Log4j2
public class FavoriteProductServiceImpl implements FavoriteProductService {

    private final FavoriteProductServiceConnectionProperty favoriteProductServiceConnectionProperty;

    private final WebClient favoriteWebClient;

    private final UserFavoriteRepository userFavoriteRepository;

    private final ProductCardSliderService productCardSliderService;

    @Override
    public void createFavorite(String chatId) {
        Path path = Path.of(
                favoriteProductServiceConnectionProperty.getPath()
        );

        configWebClientWithBodyString(path, favoriteWebClient.post(), chatId)
                .subscribe();
    }

    @Override
    public boolean existProductInFavorite(String chatId, ProductDto productDto) {
        return userFavoriteRepository
                .findById(chatId)
                .map(UserFavorite::getFavoriteProducts)
                .orElse(Collections.emptySet())
                .contains(productDto);
    }

    @Override
    public void getUserFavorite(String chatId) {
        Path path = Path.of(
                favoriteProductServiceConnectionProperty.getPath(),
                chatId
        );

        // Create new product card slider
        productCardSliderService.create(chatId);

        configWebClient(path, favoriteWebClient.get())
                .subscribe(getFindSubscriber(chatId));
    }

    @Override
    public void addToFavorite(String chatId) {
        Path path = Path.of(
                favoriteProductServiceConnectionProperty.getPath(),
                chatId,
                "add",
                "product"
        );

        configWebClientWithBodyProductDto(path, favoriteWebClient.patch(), productCardSliderService.getCurrentProduct(chatId))
                .subscribe(getUpdateSubscriber(chatId));
    }


    @Override
    public void deleteFromFavorite(String chatId) {
        Path path = Path.of(
                favoriteProductServiceConnectionProperty.getPath(),
                chatId,
                "del",
                "product"
        );

        configWebClientWithBodyProductDto(path, favoriteWebClient.patch(), productCardSliderService.getCurrentProduct(chatId))
                .subscribe(getUpdateSubscriber(chatId));
    }

    private Subscriber<ProductDto> getUpdateSubscriber(String chatId) {
        Set<ProductDto> favorites = new HashSet<>();

        return new Subscriber<ProductDto>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(ProductDto productDto) {
                favorites.add(productDto);
            }

            @Override
            public void onError(Throwable throwable) {
                log.info(throwable.getMessage());
            }

            @Override
            public void onComplete() {
                userFavoriteRepository.save(new UserFavorite(chatId, favorites));
                productCardSliderService.update(chatId, ProductCardSlider::refresh);
            }
        };
    }

    private Subscriber<ProductDto> getFindSubscriber(String chatId) {
        Set<ProductDto> favorites = new HashSet<>();

        return new Subscriber<ProductDto>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(ProductDto productDto) {
                favorites.add(productDto);
                productCardSliderService.update(chatId, p -> p.addProduct(productDto));
            }

            @Override
            public void onError(Throwable throwable) {
                log.info(throwable.getMessage());
            }

            @Override
            public void onComplete() {
                userFavoriteRepository.save(new UserFavorite(chatId, favorites));
                productCardSliderService.update(chatId, ProductCardSlider::refresh);
            }
        };
    }

    private Flux<ProductDto> configWebClientWithBodyProductDto(Path path, WebClient.RequestBodyUriSpec bodyUriSpec, ProductDto body) {
        return bodyUriSpec
                .uri(uriBuilder -> uriBuilder
                        .path(path.toString())
                        .build())
                .body(BodyInserters.fromValue(body))
                .accept(MediaType.valueOf(MediaType.TEXT_EVENT_STREAM_VALUE))
                .retrieve()
                .bodyToFlux(ProductDto.class);
    }

    private Flux<Boolean> configWebClientWithBodyString(Path path, WebClient.RequestBodyUriSpec bodyUriSpec, String body) {
        return bodyUriSpec
                .uri(uriBuilder -> uriBuilder
                        .path(path.toString())
                        .build())
                .body(BodyInserters.fromValue(body))
                .accept(MediaType.valueOf(MediaType.TEXT_EVENT_STREAM_VALUE))
                .retrieve()
                .bodyToFlux(Boolean.class);
    }

    private Flux<ProductDto> configWebClient(Path path, WebClient.RequestHeadersUriSpec<?> headersUriSpec) {
        return headersUriSpec
                .uri(uriBuilder -> uriBuilder
                        .path(path.toString())
                        .build())
                .accept(MediaType.valueOf(MediaType.TEXT_EVENT_STREAM_VALUE))
                .retrieve()
                .bodyToFlux(ProductDto.class);
    }

}
