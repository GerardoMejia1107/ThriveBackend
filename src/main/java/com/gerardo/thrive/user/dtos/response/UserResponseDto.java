package com.gerardo.thrive.user.dtos.response;

import com.gerardo.thrive.common.enums.Role;
import lombok.Builder;

@Builder
public record UserResponseDto(
        Long id,
        String name,
        String username,
        String email,
        Role role,
        String token,
        String refreshToken
) {
}
