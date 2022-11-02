package ru.spaceprice.product.search.service;

import reactor.core.publisher.Flux;
import ru.spaceprice.dto.ProductDto;

public interface ProductSearchService {

    Flux<ProductDto> findProductsByName(String searchName);
}
