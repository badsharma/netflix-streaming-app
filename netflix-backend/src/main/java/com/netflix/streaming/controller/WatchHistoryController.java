package com.netflix.streaming.controller;

import com.netflix.streaming.dto.request.WatchProgressRequest;
import com.netflix.streaming.dto.response.ApiResponse;
import com.netflix.streaming.model.WatchHistory;
import com.netflix.streaming.security.UserPrincipal;
import com.netflix.streaming.service.WatchHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class WatchHistoryController {

    private final WatchHistoryService watchHistoryService;

    @GetMapping
    public ResponseEntity<Page<WatchHistory>> getWatchHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        Page<WatchHistory> history = watchHistoryService.getWatchHistory(userPrincipal.getId(), page, size);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/continue-watching")
    public ResponseEntity<List<WatchHistory>> getContinueWatching(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        List<WatchHistory> history = watchHistoryService.getContinueWatching(userPrincipal.getId());
        return ResponseEntity.ok(history);
    }

    @PostMapping("/{movieId}/progress")
    public ResponseEntity<WatchHistory> saveWatchProgress(
            @PathVariable String movieId,
            @Valid @RequestBody WatchProgressRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        WatchHistory history = watchHistoryService.saveWatchProgress(
                userPrincipal.getId(),
                movieId,
                request
        );
        return ResponseEntity.ok(history);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<WatchHistory> getWatchProgress(
            @PathVariable String movieId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        WatchHistory history = watchHistoryService.getWatchProgress(userPrincipal.getId(), movieId);
        return ResponseEntity.ok(history);
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<ApiResponse> deleteWatchHistory(
            @PathVariable String movieId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        watchHistoryService.deleteWatchHistory(userPrincipal.getId(), movieId);
        return ResponseEntity.ok(new ApiResponse(true, "Watch history deleted successfully"));
    }
}
