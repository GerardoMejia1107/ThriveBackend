package com.gerardo.thrive.auth.controllers;

import com.gerardo.thrive.auth.dtos.request.LoginRequestDto;
import com.gerardo.thrive.auth.dtos.request.RefreshTokenRequestDto;
import com.gerardo.thrive.auth.dtos.request.RegisterRequestDto;
import com.gerardo.thrive.auth.dtos.response.RefreshAccessTokenResponseDto;
import com.gerardo.thrive.auth.services.AuthService;
import com.gerardo.thrive.auth.services.RefreshTokenService;
import com.gerardo.thrive.common.dtos.ApiResponse;
import com.gerardo.thrive.user.dtos.response.UserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/security/user/test")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> registerUser(@Valid @RequestBody RegisterRequestDto request) {
        UserResponseDto user = authService.registerUser(request);
        ApiResponse<UserResponseDto> response = ApiResponse.created(user, "USER_CREATED");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponseDto>> login(@Valid @RequestBody LoginRequestDto request) {
        UserResponseDto user = authService.loginUser(request);
        ApiResponse<UserResponseDto> response = ApiResponse.created(user, "USER_LOGIN");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<RefreshAccessTokenResponseDto>> refresh(
            @Valid @RequestBody RefreshTokenRequestDto rawRefreshToken) throws NoSuchAlgorithmException {
        RefreshAccessTokenResponseDto refresh = refreshTokenService.refreshAccessToken(rawRefreshToken.refreshToken());
        ApiResponse<RefreshAccessTokenResponseDto> response = ApiResponse.created(refresh, "REFRESH_TOKEN_RETRIEVED");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @Valid @RequestBody RefreshTokenRequestDto rawRefreshToken) throws NoSuchAlgorithmException {
        refreshTokenService.logout(rawRefreshToken.refreshToken());
        return ResponseEntity.ok(ApiResponse.ok(null, "LOG_OUT"));
    }
}
