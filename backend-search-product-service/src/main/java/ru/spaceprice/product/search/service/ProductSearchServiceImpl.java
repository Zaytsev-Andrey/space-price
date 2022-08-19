package ru.spaceprice.product.search.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import ru.spaceprice.dto.ProductDto;
import ru.spaceprice.parser.Parser;

import java.util.Map;

@Service
@Log4j2
public class ProductSearchServiceImpl implements ProductSearchService {

    private final Map<String, Parser> activeParserMap;

    @Autowired
    public ProductSearchServiceImpl(@Qualifier("activeParserMap") Map<String, Parser> activeParserMap) {
        this.activeParserMap = activeParserMap;
    }

    @Override
    public Flux<ProductDto> findProductsByName(String searchName) {
        log.info("Search requests for products '{}'", searchName);
        return Flux.fromIterable(activeParserMap.values())
                .flatMap(parser -> parser.parseAllProducts(searchName)
                        .subscribeOn(Schedulers.parallel())
                );
    }

}
