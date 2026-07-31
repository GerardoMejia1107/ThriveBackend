package com.gerardo.thrive.user.dtos.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDto(
        @NotBlank(message = "User name is required")
        String name,
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "A username is required")
        String username,
        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 32, message = "Password length must be larger or equal to 8 and less than 32 characters")
        String password
) {
}
