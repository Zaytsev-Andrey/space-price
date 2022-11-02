package ru.spaceprice.telegram.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.reactivestreams.Subscription;
import org.springframework.stereotype.Service;
import ru.spaceprice.telegram.storage.repository.ProductSubscriptionRepository;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductSubscriptionServiceImpl implements ProductSubscriptionService {

    private static final int COUNT_PRODUCT_FOR_LOADING = 5;

    private final ProductSubscriptionRepository productSubscriptionRepository;

    @Override
    public void registrationSubscription(String id, Subscription subscription) {
        productSubscriptionRepository.save(id, subscription);
    }

    @Override
    public void loadProducts(String id) {
        productSubscriptionRepository.findById(id)
                .ifPresent(Subscription -> Subscription.request(COUNT_PRODUCT_FOR_LOADING));
    }

    @Override
    public void closeSubscription(String id) {
        log.info("Connection from chatId='{}' closed", id);
        productSubscriptionRepository.deleteById(id)
                .ifPresent(Subscription::cancel);
    }
}
