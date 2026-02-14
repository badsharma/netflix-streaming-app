package com.netflix.streaming.dto.response;

import com.netflix.streaming.model.enums.SubscriptionTier;
import com.netflix.streaming.model.enums.VideoQuality;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionResponse {
    private String id;
    private SubscriptionTier tier;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean active;
    private Integer maxDevices;
    private List<VideoQuality> allowedQualities;
    private Integer maxMovieAccess;
}
