package com.netflix.streaming.dto.response;

import com.netflix.streaming.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String email;
    private String username;
    private Role role;
    private SubscriptionResponse subscription;
    private LocalDateTime createdAt;
    private String profileImage;
}
