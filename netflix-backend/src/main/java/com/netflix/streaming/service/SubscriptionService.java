package com.netflix.streaming.service;

import com.netflix.streaming.dto.request.SubscriptionRequest;
import com.netflix.streaming.dto.response.SubscriptionResponse;
import com.netflix.streaming.exception.BadRequestException;
import com.netflix.streaming.exception.ResourceNotFoundException;
import com.netflix.streaming.model.Subscription;
import com.netflix.streaming.model.User;
import com.netflix.streaming.model.enums.SubscriptionTier;
import com.netflix.streaming.model.enums.VideoQuality;
import com.netflix.streaming.repository.SubscriptionRepository;
import com.netflix.streaming.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public Subscription createSubscription(String userId, SubscriptionTier tier) {
        Subscription subscription = new Subscription();
        subscription.setUserId(userId);
        subscription.setTier(tier);
        subscription.setStartDate(LocalDateTime.now());
        subscription.setEndDate(LocalDateTime.now().plusYears(1));
        subscription.setActive(true);
        subscription.setCreatedAt(LocalDateTime.now());
        subscription.setUpdatedAt(LocalDateTime.now());

        configureSubscriptionFeatures(subscription, tier);

        return subscriptionRepository.save(subscription);
    }

    public SubscriptionResponse getSubscriptionByUserId(String userId) {
        Subscription subscription = subscriptionRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found for user"));

        return convertToResponse(subscription);
    }

    public SubscriptionResponse changeSubscriptionTier(String userId, SubscriptionRequest request) {
        Subscription subscription = subscriptionRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found for user"));

        if (subscription.getTier() == request.getTier()) {
            throw new BadRequestException("User already has this subscription tier");
        }

        subscription.setTier(request.getTier());
        subscription.setUpdatedAt(LocalDateTime.now());
        configureSubscriptionFeatures(subscription, request.getTier());

        Subscription updated = subscriptionRepository.save(subscription);
        return convertToResponse(updated);
    }

    public List<SubscriptionPlan> getAvailablePlans() {
        return Arrays.asList(
                new SubscriptionPlan(
                        SubscriptionTier.BASIC,
                        "Basic",
                        "Free",
                        Arrays.asList("Limited movie catalog", "SD quality", "1 device"),
                        1,
                        Arrays.asList(VideoQuality.SD),
                        100
                ),
                new SubscriptionPlan(
                        SubscriptionTier.STANDARD,
                        "Standard",
                        "Free",
                        Arrays.asList("More movies", "HD quality", "2 devices"),
                        2,
                        Arrays.asList(VideoQuality.SD, VideoQuality.HD),
                        500
                ),
                new SubscriptionPlan(
                        SubscriptionTier.PREMIUM,
                        "Premium",
                        "Free",
                        Arrays.asList("All movies", "4K quality", "4 devices"),
                        4,
                        Arrays.asList(VideoQuality.SD, VideoQuality.HD, VideoQuality.UHD_4K),
                        null
                )
        );
    }

    public boolean canAccessMovie(String userId, SubscriptionTier movieTier) {
        Subscription subscription = subscriptionRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

        if (!subscription.isActive()) {
            return false;
        }

        return hasAccessToTier(subscription.getTier(), movieTier);
    }

    public List<VideoQuality> getAllowedQualities(String userId) {
        Subscription subscription = subscriptionRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

        return subscription.getAllowedQualities();
    }

    private void configureSubscriptionFeatures(Subscription subscription, SubscriptionTier tier) {
        switch (tier) {
            case BASIC:
                subscription.setMaxDevices(1);
                subscription.setAllowedQualities(List.of(VideoQuality.SD));
                subscription.setMaxMovieAccess(100);
                break;
            case STANDARD:
                subscription.setMaxDevices(2);
                subscription.setAllowedQualities(Arrays.asList(VideoQuality.SD, VideoQuality.HD));
                subscription.setMaxMovieAccess(500);
                break;
            case PREMIUM:
                subscription.setMaxDevices(4);
                subscription.setAllowedQualities(Arrays.asList(VideoQuality.SD, VideoQuality.HD, VideoQuality.UHD_4K));
                subscription.setMaxMovieAccess(null);
                break;
        }
    }

    private boolean hasAccessToTier(SubscriptionTier userTier, SubscriptionTier movieTier) {
        int userLevel = getTierLevel(userTier);
        int movieLevel = getTierLevel(movieTier);
        return userLevel >= movieLevel;
    }

    private int getTierLevel(SubscriptionTier tier) {
        return switch (tier) {
            case BASIC -> 1;
            case STANDARD -> 2;
            case PREMIUM -> 3;
        };
    }

    private SubscriptionResponse convertToResponse(Subscription subscription) {
        return SubscriptionResponse.builder()
                .id(subscription.getId())
                .tier(subscription.getTier())
                .startDate(subscription.getStartDate())
                .endDate(subscription.getEndDate())
                .active(subscription.isActive())
                .maxDevices(subscription.getMaxDevices())
                .allowedQualities(subscription.getAllowedQualities())
                .maxMovieAccess(subscription.getMaxMovieAccess())
                .build();
    }

    public record SubscriptionPlan(
            SubscriptionTier tier,
            String name,
            String price,
            List<String> features,
            Integer maxDevices,
            List<VideoQuality> qualities,
            Integer maxMovies
    ) {}
}
