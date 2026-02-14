package com.netflix.streaming.model;

import com.netflix.streaming.model.enums.SubscriptionTier;
import com.netflix.streaming.model.enums.VideoQuality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "movies")
public class Movie {

    @Id
    private String id;

    @Indexed
    private String title;

    private String description;

    @Indexed
    private List<String> genres;

    private Integer releaseYear;

    private String thumbnailUrl;

    private String trailerUrl;

    private String videoUrl;

    private Map<VideoQuality, String> videoUrls;

    @Indexed
    private SubscriptionTier minimumTier;

    private Integer durationMinutes;

    private Double rating;

    private boolean featured;

    private boolean trending;

    private Long viewCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String addedBy;
}
