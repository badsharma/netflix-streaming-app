package com.netflix.streaming.service;

import com.netflix.streaming.dto.request.WatchProgressRequest;
import com.netflix.streaming.model.WatchHistory;
import com.netflix.streaming.repository.WatchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchHistoryService {

    private final WatchHistoryRepository watchHistoryRepository;

    public WatchHistory saveWatchProgress(String userId, String movieId, WatchProgressRequest request) {
        WatchHistory history = watchHistoryRepository.findByUserIdAndMovieId(userId, movieId)
                .orElse(new WatchHistory());

        if (history.getId() == null) {
            history.setUserId(userId);
            history.setMovieId(movieId);
            history.setFirstWatchedAt(LocalDateTime.now());
            history.setWatchCount(1);
        } else {
            history.setWatchCount(history.getWatchCount() + 1);
        }

        history.setProgressSeconds(request.getProgressSeconds());
        history.setTotalDurationSeconds(request.getTotalDurationSeconds());
        history.setWatchedQuality(request.getQuality());
        history.setLastWatchedAt(LocalDateTime.now());

        double watchPercentage = (double) request.getProgressSeconds() / request.getTotalDurationSeconds() * 100;
        history.setCompleted(watchPercentage >= 90);

        return watchHistoryRepository.save(history);
    }

    public Page<WatchHistory> getWatchHistory(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("lastWatchedAt").descending());
        return watchHistoryRepository.findByUserId(userId, pageable);
    }

    public List<WatchHistory> getContinueWatching(String userId) {
        return watchHistoryRepository.findByUserIdAndCompletedFalseOrderByLastWatchedAtDesc(userId);
    }

    public void deleteWatchHistory(String userId, String movieId) {
        watchHistoryRepository.deleteByUserIdAndMovieId(userId, movieId);
    }

    public WatchHistory getWatchProgress(String userId, String movieId) {
        return watchHistoryRepository.findByUserIdAndMovieId(userId, movieId)
                .orElse(null);
    }
}
