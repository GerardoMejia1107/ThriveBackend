package com.gerardo.thrive.user.dtos.response;

import lombok.Builder;

@Builder
public record UserResponseDto(
        Long id,
        String name,
        String username,
        String email
) {
}
