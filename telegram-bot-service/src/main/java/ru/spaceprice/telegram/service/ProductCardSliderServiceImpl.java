package ru.spaceprice.telegram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spaceprice.dto.ProductDto;
import ru.spaceprice.telegram.storage.entity.ProductCardSlider;
import ru.spaceprice.telegram.storage.repository.ProductCardSliderRepository;

import java.util.Optional;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class ProductCardSliderServiceImpl implements ProductCardSliderService {

    private final ProductCardSliderRepository productCardSliderRepository;

    @Override
    public void create(String chatId) {
        // disable previous card slider if present
        Integer messageId = productCardSliderRepository
                .findById(chatId)
                .orElse(new ProductCardSlider())
                .getMessageId();
        if (messageId != null) {
            update(chatId, ProductCardSlider::disable);
        }
        // create and save new card slider
        productCardSliderRepository.save(ProductCardSlider.newProductCardSlider(chatId));
    }

    @Override
    public void update(String chatId, Consumer<ProductCardSlider> consumer) {
        Optional<ProductCardSlider> productCardSliderOpt = productCardSliderRepository.findById(chatId);
        if (productCardSliderOpt.isPresent()) {
            ProductCardSlider productCardSlider = productCardSliderOpt.get();
            consumer.accept(productCardSlider);
            productCardSliderRepository.save(productCardSlider);
        }
    }

    @Override
    public ProductDto getCurrentProduct(String chatId) {
        return productCardSliderRepository.findById(chatId)
                .orElseThrow(IllegalArgumentException::new)
                .navigator()
                .getCurrentProduct();
    }

    @Override
    public long getSize(String chatId) {
        return productCardSliderRepository
                .findById(chatId)
                .orElse(new ProductCardSlider())
                .getProducts()
                .size();
    }

}
