package com.netflix.streaming.controller;

import com.netflix.streaming.model.enums.VideoQuality;
import com.netflix.streaming.security.UserPrincipal;
import com.netflix.streaming.service.MovieService;
import com.netflix.streaming.service.StreamingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/streaming")
@RequiredArgsConstructor
public class StreamingController {

    private final StreamingService streamingService;
    private final MovieService movieService;

    @GetMapping("/{movieId}/validate")
    public ResponseEntity<Map<String, Object>> validateStreamingAccess(
            @PathVariable String movieId,
            @RequestParam(required = false) VideoQuality quality,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        Map<String, Object> result = streamingService.validateStreamingAccess(
                userPrincipal.getId(),
                movieId,
                quality
        );
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{movieId}/stream")
    public ResponseEntity<Map<String, Object>> getStreamUrl(
            @PathVariable String movieId,
            @RequestParam(required = false, defaultValue = "SD") VideoQuality quality,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        Map<String, Object> result = streamingService.getStreamUrl(
                userPrincipal.getId(),
                movieId,
                quality
        );

        movieService.incrementViewCount(movieId);

        return ResponseEntity.ok(result);
    }
}
