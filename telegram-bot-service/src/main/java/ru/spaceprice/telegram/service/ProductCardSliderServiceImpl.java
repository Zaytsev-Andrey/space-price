package ru.spaceprice.telegram.service;

import lombok.RequiredArgsConstructor;
import org.reactivestreams.Subscription;
import org.springframework.stereotype.Service;
import ru.spaceprice.dto.ProductDto;
import ru.spaceprice.telegram.storage.entity.ProductCardSlider;
import ru.spaceprice.telegram.storage.repository.ProductCardSliderRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductCardSliderServiceImpl implements ProductCardSliderService {

    private final ProductCardSliderRepository productCardSliderRepository;

    @Override
    public void create(String chatId) {
        // disable previous card slider
        disableProductCardSlider(chatId);
        // create and save new card slider
        productCardSliderRepository.save(ProductCardSlider.newProductCardSlider(chatId));
    }

    private void disableProductCardSlider(String chatId) {
        Optional<ProductCardSlider> productCardSliderOpt = productCardSliderRepository.findById(chatId);
        if (productCardSliderOpt.isPresent()) {
            ProductCardSlider productCardSlider = productCardSliderOpt.get();
            productCardSlider.disable();
            productCardSliderRepository.save(productCardSlider);
        }
    }

    @Override
    public void addProduct(String chatId, ProductDto productDto) {
        Optional<ProductCardSlider> productCardSliderOpt = productCardSliderRepository.findById(chatId);
        if (productCardSliderOpt.isPresent()) {
            ProductCardSlider productCardSlider = productCardSliderOpt.get();
            productCardSlider.addProduct(productDto);
            productCardSliderRepository.save(productCardSlider);
        }
    }

    @Override
    public void registrationMessage(String chatId, Integer messageId) {
        Optional<ProductCardSlider> productCardSliderOpt = productCardSliderRepository.findById(chatId);
        if (productCardSliderOpt.isPresent()) {
            ProductCardSlider productCardSlider = productCardSliderOpt.get();
            if (productCardSlider.getMessageId() == null) {
                productCardSlider.setMessageId(messageId);
                productCardSliderRepository.save(productCardSlider);
            }
        }
    }

}
