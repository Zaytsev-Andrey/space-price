package ru.spaceprice.telegram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.spaceprice.dto.ProductDto;
import ru.spaceprice.telegram.property.SearchProductServiceConnectionProperty;

@Service
@RequiredArgsConstructor
public class ProductSearchServiceImpl implements ProductSearchService {

    private final SearchProductServiceConnectionProperty searchProductServiceConnectionProperty;

    private final WebClient searchWebClient;

    @Override
    public Flux<ProductDto> findProducts(String searchName) {
        return searchWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(searchProductServiceConnectionProperty.getPath())
                        .queryParam(searchProductServiceConnectionProperty.getParamName(), searchName)
                        .build()
                )
                .accept(MediaType.valueOf(MediaType.TEXT_EVENT_STREAM_VALUE))
                .retrieve()
                .bodyToFlux(ProductDto.class);
    }
}
