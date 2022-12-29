package ru.spaceprice.product.favorite.service;

import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.spaceprice.product.favorite.collection.Favorite;
import ru.spaceprice.dto.ProductDto;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final ReactiveMongoTemplate mongoTemplate;

    private final ProductService productService;

    private final ModelMapper modelMapper;

    @Override
    public Flux<ProductDto> findUserProducts(String userId) {
        return mongoTemplate.findOne(
                new Query(Criteria.where("userId").is(userId)),
                Favorite.class
        )
        .map(Favorite::getProductIds)
        .flatMapMany(productService::findByIds)
        .map(p -> modelMapper.map(p, ProductDto.class));
    }

    @Override
    public Mono<Boolean> createFavorite(String userId) {
        return mongoTemplate.upsert(
                new Query(Criteria.where("userId").is(userId)),
                new Update().setOnInsert("userId", userId),
                Favorite.class
        ).map(UpdateResult::wasAcknowledged);
    }

    @Transactional
    @Override
    public Flux<ProductDto> addProductToUserFavorites(String userId, ProductDto productDto) {
        return productService.findOrInsertProduct(productDto)
                .flatMapMany(
                        product -> mongoTemplate.findAndModify(
                                new Query(Criteria.where("userId").is(userId)),
                                new Update().addToSet("productIds", product.getId()),
                                new FindAndModifyOptions()
                                        .upsert(true)
                                        .returnNew(true),
                                Favorite.class
                        )
                )
                .map(Favorite::getProductIds)
                .flatMap(productService::findByIds)
                .map(p -> modelMapper.map(p, ProductDto.class));
    }

    @Override
    public Flux<ProductDto> deleteProductFromUserFavorite(String userId, ProductDto productDto) {
        return productService.findOrInsertProduct(productDto)
                .flatMapMany(
                        product -> mongoTemplate.findAndModify(
                                new Query(Criteria.where("userId").is(userId)),
                                new Update().pull("productIds", product.getId()),
                                new FindAndModifyOptions().returnNew(true),
                                Favorite.class
                        )
                )
                .map(Favorite::getProductIds)
                .flatMap(productService::findByIds)
                .map(p -> modelMapper.map(p, ProductDto.class));
    }

}
