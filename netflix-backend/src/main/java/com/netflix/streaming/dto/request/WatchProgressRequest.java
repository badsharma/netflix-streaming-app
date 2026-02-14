package com.netflix.streaming.dto.request;

import com.netflix.streaming.model.enums.VideoQuality;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WatchProgressRequest {

    @NotNull(message = "Progress seconds is required")
    private Integer progressSeconds;

    @NotNull(message = "Total duration seconds is required")
    private Integer totalDurationSeconds;

    @NotNull(message = "Quality is required")
    private VideoQuality quality;
}
