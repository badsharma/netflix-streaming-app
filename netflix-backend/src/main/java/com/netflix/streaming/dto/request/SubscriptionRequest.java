package com.netflix.streaming.dto.request;

import com.netflix.streaming.model.enums.SubscriptionTier;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionRequest {

    @NotNull(message = "Subscription tier is required")
    private SubscriptionTier tier;
}
