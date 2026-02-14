package com.netflix.streaming.repository;

import com.netflix.streaming.model.Subscription;
import com.netflix.streaming.model.enums.SubscriptionTier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends MongoRepository<Subscription, String> {

    Optional<Subscription> findByUserId(String userId);

    List<Subscription> findByTier(SubscriptionTier tier);

    List<Subscription> findByActive(boolean active);
}
