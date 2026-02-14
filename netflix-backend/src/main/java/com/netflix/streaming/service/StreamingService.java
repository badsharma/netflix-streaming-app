package com.netflix.streaming.service;

import com.netflix.streaming.exception.InvalidSubscriptionException;
import com.netflix.streaming.exception.ResourceNotFoundException;
import com.netflix.streaming.model.Movie;
import com.netflix.streaming.model.Subscription;
import com.netflix.streaming.model.enums.SubscriptionTier;
import com.netflix.streaming.model.enums.VideoQuality;
import com.netflix.streaming.repository.MovieRepository;
import com.netflix.streaming.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StreamingService {

    private final MovieRepository movieRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionService subscriptionService;

    public Map<String, Object> validateStreamingAccess(String userId, String movieId, VideoQuality quality) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        Subscription subscription = subscriptionRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

        Map<String, Object> result = new HashMap<>();

        if (!subscription.isActive()) {
            result.put("canWatch", false);
            result.put("reason", "Subscription is not active");
            result.put("allowedQualities", List.of());
            return result;
        }

        boolean hasAccess = subscriptionService.canAccessMovie(userId, movie.getMinimumTier());
        if (!hasAccess) {
            result.put("canWatch", false);
            result.put("reason", "Upgrade your subscription to watch this movie");
            result.put("requiredTier", movie.getMinimumTier());
            result.put("currentTier", subscription.getTier());
            result.put("allowedQualities", List.of());
            return result;
        }

        List<VideoQuality> allowedQualities = subscription.getAllowedQualities();
        if (quality != null && !allowedQualities.contains(quality)) {
            result.put("canWatch", false);
            result.put("reason", "Your subscription does not support " + quality + " quality");
            result.put("allowedQualities", allowedQualities);
            return result;
        }

        result.put("canWatch", true);
        result.put("allowedQualities", allowedQualities);
        result.put("movieTitle", movie.getTitle());
        return result;
    }

    public Map<String, Object> getStreamUrl(String userId, String movieId, VideoQuality quality) {
        Map<String, Object> validation = validateStreamingAccess(userId, movieId, quality);

        if (!(Boolean) validation.get("canWatch")) {
            throw new InvalidSubscriptionException((String) validation.get("reason"));
        }

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        Map<String, Object> response = new HashMap<>();

        if (movie.getVideoUrls() != null && movie.getVideoUrls().containsKey(quality)) {
            response.put("streamUrl", movie.getVideoUrls().get(quality));
        } else if (movie.getVideoUrl() != null) {
            response.put("streamUrl", movie.getVideoUrl());
        } else {
            throw new ResourceNotFoundException("Video URL not found for this movie");
        }

        response.put("quality", quality);
        response.put("allowedQualities", validation.get("allowedQualities"));
        response.put("movieTitle", movie.getTitle());
        response.put("durationMinutes", movie.getDurationMinutes());

        return response;
    }
}
