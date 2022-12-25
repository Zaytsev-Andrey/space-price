package ru.spaceprice.product.favorite.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.spaceprice.dto.ProductDto;
import ru.spaceprice.product.favorite.document.Product;
import ru.spaceprice.product.favorite.repository.ProductRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    @Override
    public Mono<Product> saveProduct(Mono<ProductDto> productDto) {
        return productDto
                .map(pd -> modelMapper.map(pd, Product.class))
                .doOnNext(product -> productRepository
                        .findByProductShopIdAndShopName(product.getProductShopId(), product.getShopName())
                        .defaultIfEmpty(new Product())
                        .doOnNext(p -> product.setId(p.getId())))
                .flatMap(productRepository::save);
    }

    @Override
    public Flux<Product> findByIds(Set<String> productIds) {
        return productRepository.findAllById(productIds);
    }

}
