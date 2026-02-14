package com.netflix.streaming.dto.response;

import com.netflix.streaming.model.enums.SubscriptionTier;
import com.netflix.streaming.model.enums.VideoQuality;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponse {
    private String id;
    private String title;
    private String description;
    private List<String> genres;
    private Integer releaseYear;
    private String thumbnailUrl;
    private String trailerUrl;
    private String videoUrl;
    private Map<VideoQuality, String> videoUrls;
    private SubscriptionTier minimumTier;
    private Integer durationMinutes;
    private Double rating;
    private boolean featured;
    private boolean trending;
    private Long viewCount;
    private LocalDateTime createdAt;
}
