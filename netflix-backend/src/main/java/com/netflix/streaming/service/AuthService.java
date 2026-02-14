package com.netflix.streaming.service;

import com.netflix.streaming.dto.request.LoginRequest;
import com.netflix.streaming.dto.request.RegisterRequest;
import com.netflix.streaming.dto.response.AuthResponse;
import com.netflix.streaming.dto.response.SubscriptionResponse;
import com.netflix.streaming.dto.response.UserResponse;
import com.netflix.streaming.exception.BadRequestException;
import com.netflix.streaming.model.Subscription;
import com.netflix.streaming.model.User;
import com.netflix.streaming.model.enums.Role;
import com.netflix.streaming.model.enums.SubscriptionTier;
import com.netflix.streaming.repository.SubscriptionRepository;
import com.netflix.streaming.repository.UserRepository;
import com.netflix.streaming.security.JwtTokenProvider;
import com.netflix.streaming.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionService subscriptionService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        Subscription subscription = subscriptionService.createSubscription(
                savedUser.getId(),
                SubscriptionTier.BASIC
        );

        savedUser.setSubscriptionId(subscription.getId());
        userRepository.save(savedUser);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String token = tokenProvider.generateToken(authentication);

        SubscriptionResponse subscriptionResponse = SubscriptionResponse.builder()
                .id(subscription.getId())
                .tier(subscription.getTier())
                .startDate(subscription.getStartDate())
                .endDate(subscription.getEndDate())
                .active(subscription.isActive())
                .maxDevices(subscription.getMaxDevices())
                .allowedQualities(subscription.getAllowedQualities())
                .maxMovieAccess(subscription.getMaxMovieAccess())
                .build();

        UserResponse userResponse = UserResponse.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .username(savedUser.getUsername())
                .role(savedUser.getRole())
                .subscription(subscriptionResponse)
                .createdAt(savedUser.getCreatedAt())
                .build();

        return new AuthResponse(token, userResponse);
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new BadRequestException("User not found"));

        Subscription subscription = subscriptionRepository.findByUserId(user.getId())
                .orElseThrow(() -> new BadRequestException("Subscription not found"));

        SubscriptionResponse subscriptionResponse = SubscriptionResponse.builder()
                .id(subscription.getId())
                .tier(subscription.getTier())
                .startDate(subscription.getStartDate())
                .endDate(subscription.getEndDate())
                .active(subscription.isActive())
                .maxDevices(subscription.getMaxDevices())
                .allowedQualities(subscription.getAllowedQualities())
                .maxMovieAccess(subscription.getMaxMovieAccess())
                .build();

        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole())
                .subscription(subscriptionResponse)
                .createdAt(user.getCreatedAt())
                .profileImage(user.getProfileImage())
                .build();

        return new AuthResponse(token, userResponse);
    }

    public UserResponse getCurrentUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));

        Subscription subscription = subscriptionRepository.findByUserId(user.getId())
                .orElseThrow(() -> new BadRequestException("Subscription not found"));

        SubscriptionResponse subscriptionResponse = SubscriptionResponse.builder()
                .id(subscription.getId())
                .tier(subscription.getTier())
                .startDate(subscription.getStartDate())
                .endDate(subscription.getEndDate())
                .active(subscription.isActive())
                .maxDevices(subscription.getMaxDevices())
                .allowedQualities(subscription.getAllowedQualities())
                .maxMovieAccess(subscription.getMaxMovieAccess())
                .build();

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole())
                .subscription(subscriptionResponse)
                .createdAt(user.getCreatedAt())
                .profileImage(user.getProfileImage())
                .build();
    }
}
