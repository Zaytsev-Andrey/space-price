package ru.spaceprice.telegram.service;

import org.reactivestreams.Subscription;

public interface ProductSubscriptionService {

    void registrationSubscription(String id, Subscription subscription);

    void loadProducts(String id);

    void closeSubscription(String id);
}
