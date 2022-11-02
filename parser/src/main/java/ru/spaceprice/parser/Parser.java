package ru.spaceprice.parser;

import reactor.core.publisher.Flux;
import ru.spaceprice.dto.ProductDto;

public interface Parser {

    Flux<ProductDto> parseAllProducts(String searchName);

    String getShopName();
}
