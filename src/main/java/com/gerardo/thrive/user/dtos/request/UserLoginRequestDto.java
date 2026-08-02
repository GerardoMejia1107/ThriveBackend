package com.gerardo.thrive.user.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequestDto(
        @NotBlank String email,
        @NotBlank String password
) {
}
