package com.netflix.streaming.model;

import com.netflix.streaming.model.enums.VideoQuality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "watch_history")
public class WatchHistory {

    @Id
    private String id;

    @Indexed
    private String userId;

    @Indexed
    private String movieId;

    private Integer progressSeconds;

    private Integer totalDurationSeconds;

    private VideoQuality watchedQuality;

    private LocalDateTime lastWatchedAt;

    private LocalDateTime firstWatchedAt;

    private boolean completed;

    private Integer watchCount;
}
