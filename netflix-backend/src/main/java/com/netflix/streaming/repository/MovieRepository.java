package com.netflix.streaming.repository;

import com.netflix.streaming.model.Movie;
import com.netflix.streaming.model.enums.SubscriptionTier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {

    Page<Movie> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Movie> findByGenresContaining(String genre, Pageable pageable);

    Page<Movie> findByMinimumTier(SubscriptionTier tier, Pageable pageable);

    List<Movie> findByFeaturedTrue();

    List<Movie> findByTrendingTrue();

    Page<Movie> findByTitleContainingIgnoreCaseAndGenresContaining(String title, String genre, Pageable pageable);

    Page<Movie> findByMinimumTierIn(List<SubscriptionTier> tiers, Pageable pageable);
}
