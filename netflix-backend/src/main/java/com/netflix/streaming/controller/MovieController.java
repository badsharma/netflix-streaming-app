package com.netflix.streaming.controller;

import com.netflix.streaming.dto.request.MovieRequest;
import com.netflix.streaming.dto.response.ApiResponse;
import com.netflix.streaming.dto.response.MovieResponse;
import com.netflix.streaming.model.Subscription;
import com.netflix.streaming.model.enums.SubscriptionTier;
import com.netflix.streaming.repository.SubscriptionRepository;
import com.netflix.streaming.security.UserPrincipal;
import com.netflix.streaming.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final SubscriptionRepository subscriptionRepository;

    @GetMapping
    public ResponseEntity<Page<MovieResponse>> getAllMovies(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String genre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        SubscriptionTier userTier = null;
        if (userPrincipal != null) {
            Subscription subscription = subscriptionRepository.findByUserId(userPrincipal.getId()).orElse(null);
            if (subscription != null) {
                userTier = subscription.getTier();
            }
        }

        Page<MovieResponse> movies = movieService.getAllMovies(search, genre, userTier, page, size);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable String id) {
        MovieResponse movie = movieService.getMovieById(id);
        return ResponseEntity.ok(movie);
    }

    @GetMapping("/featured")
    public ResponseEntity<List<MovieResponse>> getFeaturedMovies() {
        List<MovieResponse> movies = movieService.getFeaturedMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/trending")
    public ResponseEntity<List<MovieResponse>> getTrendingMovies() {
        List<MovieResponse> movies = movieService.getTrendingMovies();
        return ResponseEntity.ok(movies);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieResponse> createMovie(
            @Valid @RequestBody MovieRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        MovieResponse movie = movieService.createMovie(request, userPrincipal.getId());
        return ResponseEntity.ok(movie);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieResponse> updateMovie(
            @PathVariable String id,
            @Valid @RequestBody MovieRequest request
    ) {
        MovieResponse movie = movieService.updateMovie(id, request);
        return ResponseEntity.ok(movie);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteMovie(@PathVariable String id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok(new ApiResponse(true, "Movie deleted successfully"));
    }
}
