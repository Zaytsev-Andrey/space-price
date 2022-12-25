package ru.spaceprice.product.favorite.repository;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.spaceprice.product.favorite.document.Product;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    Mono<Product> findByProductShopIdAndShopName(String productShopId, String shopName);
}
