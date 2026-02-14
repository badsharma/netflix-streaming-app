package com.netflix.streaming.repository;

import com.netflix.streaming.model.WatchHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WatchHistoryRepository extends MongoRepository<WatchHistory, String> {

    Optional<WatchHistory> findByUserIdAndMovieId(String userId, String movieId);

    Page<WatchHistory> findByUserId(String userId, Pageable pageable);

    List<WatchHistory> findByUserIdAndCompletedFalseOrderByLastWatchedAtDesc(String userId);

    void deleteByUserIdAndMovieId(String userId, String movieId);
}
