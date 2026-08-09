package com.gerardo.thrive.auth.dtos.response;

import lombok.Builder;

@Builder
public record ErrorResponse(
        String path, String code, String message, Object rejectedValue
) {
}
