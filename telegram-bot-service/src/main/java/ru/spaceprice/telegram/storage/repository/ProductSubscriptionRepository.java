package ru.spaceprice.telegram.storage.repository;

import org.reactivestreams.Subscription;

import java.util.Optional;

public interface ProductSubscriptionRepository {

    void save(String id, Subscription subscription);

    Optional<Subscription> findById(String id);

    Optional<Subscription> deleteById(String id);
}
