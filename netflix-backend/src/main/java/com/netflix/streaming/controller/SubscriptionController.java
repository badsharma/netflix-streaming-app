package com.netflix.streaming.controller;

import com.netflix.streaming.dto.request.SubscriptionRequest;
import com.netflix.streaming.dto.response.ApiResponse;
import com.netflix.streaming.dto.response.SubscriptionResponse;
import com.netflix.streaming.security.UserPrincipal;
import com.netflix.streaming.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/plans")
    public ResponseEntity<List<SubscriptionService.SubscriptionPlan>> getAvailablePlans() {
        List<SubscriptionService.SubscriptionPlan> plans = subscriptionService.getAvailablePlans();
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/current")
    public ResponseEntity<SubscriptionResponse> getCurrentSubscription(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        SubscriptionResponse subscription = subscriptionService.getSubscriptionByUserId(userPrincipal.getId());
        return ResponseEntity.ok(subscription);
    }

    @PostMapping("/change")
    public ResponseEntity<SubscriptionResponse> changeSubscription(
            @Valid @RequestBody SubscriptionRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        SubscriptionResponse subscription = subscriptionService.changeSubscriptionTier(
                userPrincipal.getId(),
                request
        );
        return ResponseEntity.ok(subscription);
    }
}
