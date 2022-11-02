package ru.spaceprice.telegram.storage.repository;

import org.reactivestreams.Subscription;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProductSubscriptionRepositoryImpl implements ProductSubscriptionRepository {

    private final Map<String, Subscription> subscriptions = new HashMap<>();

    @Override
    public void save(String id, Subscription subscription) {
        subscriptions.put(id, subscription);
    }

    @Override
    public Optional<Subscription> findById(String id) {
        return Optional.ofNullable(subscriptions.get(id));
    }

    @Override
    public Optional<Subscription> deleteById(String id) {
        return Optional.ofNullable(subscriptions.remove(id));
    }
}
