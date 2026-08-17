package com.gerardo.thrive.securitytest.dtos.response;

import java.util.List;

public record SecurityTestResponseDto(String username, List<String> authorities, String message) {
}
