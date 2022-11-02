package ru.spaceprice.telegram.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.spaceprice.dto.ProductDto;
import ru.spaceprice.telegram.property.SearchProductServiceConnectionProperty;

@Service
public class ProductSearchServiceImpl implements ProductSearchService {

    private final SearchProductServiceConnectionProperty searchProductServiceConnectionProperty;

    private final WebClient webClient;

    public ProductSearchServiceImpl(SearchProductServiceConnectionProperty searchProductServiceConnectionProperty) {
        this.searchProductServiceConnectionProperty = searchProductServiceConnectionProperty;
        this.webClient = WebClient.create(searchProductServiceConnectionProperty.getUri());
    }

    @Override
    public Flux<ProductDto> findProducts(String searchName) {

        return webClient
                .get()
                .uri(uriBuilder -> {
                    uriBuilder.path(searchProductServiceConnectionProperty.getPath());
                    uriBuilder.queryParam(searchProductServiceConnectionProperty.getParamName(), searchName);
                    return uriBuilder.build();
                })
                .accept(MediaType.valueOf(MediaType.TEXT_EVENT_STREAM_VALUE))
                .retrieve()
                .bodyToFlux(ProductDto.class);
    }
}
