package ru.spaceprice.product.favorite.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.spaceprice.dto.ProductDto;
import ru.spaceprice.product.favorite.collection.Product;

import java.math.BigDecimal;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<Product> findOrInsertProduct(ProductDto productDto) {
        return mongoTemplate.findAndModify(
                new Query(
                        Criteria.where("productShopId")
                                .is(productDto.getProductShopId())
                                .and("shopName")
                                .is(productDto.getShopName())
                ),
                new Update()
                        .set("name", productDto.getName())
                        .set("price", new BigDecimal(productDto.getPrice()))
                        .set("imageLink", productDto.getImageLink())
                        .set("productLink", productDto.getProductLink()),
                new FindAndModifyOptions()
                        .returnNew(true)
                        .upsert(true),
                Product.class
        );
    }

    @Override
    public Flux<Product> findByIds(Set<String> productIds) {
        return mongoTemplate.find(
                new Query(Criteria.where("_id").in(productIds)),
                Product.class
        );
    }

}
