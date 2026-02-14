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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "subscriptions")
public class Subscription {

    @Id
    private String id;

    @Indexed
    private String userId;

    private SubscriptionTier tier;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private boolean active;

    private Integer maxDevices;

    private List<VideoQuality> allowedQualities;

    private Integer maxMovieAccess;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
