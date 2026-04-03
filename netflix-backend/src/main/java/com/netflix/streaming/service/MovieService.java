package com.netflix.streaming.service;

import com.netflix.streaming.dto.request.MovieRequest;
import com.netflix.streaming.dto.response.MovieResponse;
import com.netflix.streaming.exception.ResourceNotFoundException;
import com.netflix.streaming.model.Movie;
import com.netflix.streaming.model.enums.SubscriptionTier;
import com.netflix.streaming.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieResponse createMovie(MovieRequest request, String adminId) {
        Movie movie = new Movie();
        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setGenres(request.getGenres());
        movie.setReleaseYear(request.getReleaseYear());
        movie.setThumbnailUrl(request.getThumbnailUrl());
        movie.setTrailerUrl(request.getTrailerUrl());
        movie.setVideoUrl(request.getVideoUrl());
        movie.setVideoUrls(request.getVideoUrls());
        movie.setMinimumTier(request.getMinimumTier());
        movie.setDurationMinutes(request.getDurationMinutes());
        movie.setRating(request.getRating() != null ? request.getRating() : 0.0);
        movie.setFeatured(request.isFeatured());
        movie.setTrending(request.isTrending());
        movie.setViewCount(0L);
        movie.setCreatedAt(LocalDateTime.now());
        movie.setUpdatedAt(LocalDateTime.now());
        movie.setAddedBy(adminId);

        Movie saved = movieRepository.save(movie);
        return convertToResponse(saved);
    }

    public MovieResponse updateMovie(String movieId, MovieRequest request) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setGenres(request.getGenres());
        movie.setReleaseYear(request.getReleaseYear());
        movie.setThumbnailUrl(request.getThumbnailUrl());
        movie.setTrailerUrl(request.getTrailerUrl());
        movie.setVideoUrl(request.getVideoUrl());
        movie.setVideoUrls(request.getVideoUrls());
        movie.setMinimumTier(request.getMinimumTier());
        movie.setDurationMinutes(request.getDurationMinutes());
        movie.setRating(request.getRating());
        movie.setFeatured(request.isFeatured());
        movie.setTrending(request.isTrending());
        movie.setUpdatedAt(LocalDateTime.now());

        Movie updated = movieRepository.save(movie);
        return convertToResponse(updated);
    }

    public void deleteMovie(String movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        movieRepository.delete(movie);
    }

    public MovieResponse getMovieById(String movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        return convertToResponse(movie);
    }

    public Page<MovieResponse> getAllMovies(String search, String genre, SubscriptionTier userTier,
                                           int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<Movie> movies;

        if (search != null && !search.isEmpty() && genre != null && !genre.isEmpty()) {
            movies = movieRepository.findByTitleContainingIgnoreCaseAndGenresContaining(search, genre, pageable);
        } else if (search != null && !search.isEmpty()) {
            movies = movieRepository.findByTitleContainingIgnoreCase(search, pageable);
        } else if (genre != null && !genre.isEmpty()) {
            movies = movieRepository.findByGenresContaining(genre, pageable);
        } else {
            movies = movieRepository.findAll(pageable);
        }

        if (userTier != null) {
            List<SubscriptionTier> allowedTiers = getAllowedTiers(userTier);
            List<MovieResponse> filteredContent = movies.getContent().stream()
                    .filter(movie -> allowedTiers.contains(movie.getMinimumTier()))
                    .map(this::convertToResponse)
                    .collect(Collectors.toList());
            return new PageImpl<>(filteredContent, pageable, filteredContent.size());
        }

        return movies.map(this::convertToResponse);
    }

    public List<MovieResponse> getFeaturedMovies() {
        List<Movie> movies = movieRepository.findByFeaturedTrue();
        return movies.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<MovieResponse> getTrendingMovies() {
        List<Movie> movies = movieRepository.findByTrendingTrue();
        return movies.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public void incrementViewCount(String movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        movie.setViewCount(movie.getViewCount() + 1);
        movieRepository.save(movie);
    }

    private List<SubscriptionTier> getAllowedTiers(SubscriptionTier userTier) {
        return switch (userTier) {
            case BASIC -> List.of(SubscriptionTier.BASIC);
            case STANDARD -> Arrays.asList(SubscriptionTier.BASIC, SubscriptionTier.STANDARD);
            case PREMIUM -> Arrays.asList(SubscriptionTier.BASIC, SubscriptionTier.STANDARD, SubscriptionTier.PREMIUM);
        };
    }

    private MovieResponse convertToResponse(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .genres(movie.getGenres())
                .releaseYear(movie.getReleaseYear())
                .thumbnailUrl(movie.getThumbnailUrl())
                .trailerUrl(movie.getTrailerUrl())
                .videoUrl(movie.getVideoUrl())
                .videoUrls(movie.getVideoUrls())
                .minimumTier(movie.getMinimumTier())
                .durationMinutes(movie.getDurationMinutes())
                .rating(movie.getRating())
                .featured(movie.isFeatured())
                .trending(movie.isTrending())
                .viewCount(movie.getViewCount())
                .createdAt(movie.getCreatedAt())
                .build();
    }
}
