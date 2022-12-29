package ru.spaceprice.product.favorite.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.spaceprice.product.favorite.collection.Favorite;

public interface FavoriteRepository extends ReactiveMongoRepository<Favorite, String> {

    Flux<Favorite> findByUserId(String userId);
}
