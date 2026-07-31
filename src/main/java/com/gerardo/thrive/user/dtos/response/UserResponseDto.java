package com.gerardo.thrive.user.dtos.response;

public record UserResponseDto(
        Long id,
        String name,
        String username,
        String email
) {
}
