package ru.spaceprice.telegram.service;

import reactor.core.publisher.Flux;
import ru.spaceprice.dto.ProductDto;

public interface ProductSearchService {

    Flux<ProductDto> findProducts(String searchName);
}
