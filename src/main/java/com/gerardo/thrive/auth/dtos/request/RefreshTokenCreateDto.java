package com.gerardo.thrive.auth.dtos.request;

import com.gerardo.thrive.user.entities.UserModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record RefreshTokenCreateDto(
        @NotNull(message = "Family id is required")
        UUID familyId,
        @NotBlank(message = "Token hash is required")
        String tokenHash,
        @NotNull(message = "User is required")
        UserModel user,
        @NotNull(message = "Expiration date is required")
        Instant expiresAt
) {
}
