package com.gerardo.thrive.auth.dtos.request;

import jakarta.validation.constraints.NotBlank;


public record RefreshTokenRequestDto(
        @NotBlank String refreshToken
) {
}
