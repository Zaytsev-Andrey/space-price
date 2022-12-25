package ru.spaceprice.product.favorite.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.spaceprice.dto.ProductDto;
import ru.spaceprice.product.favorite.document.Product;

import java.util.Set;

public interface ProductService {

    Mono<Product> saveProduct(Mono<ProductDto> productDto);

    Flux<Product> findByIds(Set<String> productIds);
}
