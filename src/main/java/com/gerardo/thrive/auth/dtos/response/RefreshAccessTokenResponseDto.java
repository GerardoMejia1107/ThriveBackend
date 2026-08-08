package com.gerardo.thrive.auth.dtos.response;

import lombok.Builder;

@Builder
public record RefreshAccessTokenResponseDto(
        String accessToken,
        String refreshToken
) {
}
