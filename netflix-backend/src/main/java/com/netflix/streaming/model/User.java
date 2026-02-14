package com.netflix.streaming.model;

import com.netflix.streaming.model.enums.Role;
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
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String username;

    private String password;

    @Indexed
    private Role role;

    private String subscriptionId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean active;

    private String profileImage;
}
