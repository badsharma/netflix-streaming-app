package com.netflix.streaming.dto.request;

import com.netflix.streaming.model.enums.SubscriptionTier;
import com.netflix.streaming.model.enums.VideoQuality;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Genres are required")
    private List<String> genres;

    @NotNull(message = "Release year is required")
    private Integer releaseYear;

    @NotBlank(message = "Thumbnail URL is required")
    private String thumbnailUrl;

    private String trailerUrl;

    private String videoUrl;

    private Map<VideoQuality, String> videoUrls;

    @NotNull(message = "Minimum tier is required")
    private SubscriptionTier minimumTier;

    @NotNull(message = "Duration is required")
    private Integer durationMinutes;

    private Double rating;

    private boolean featured;

    private boolean trending;
}
