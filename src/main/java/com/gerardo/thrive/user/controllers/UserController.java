package com.gerardo.thrive.user.controllers;

import com.gerardo.thrive.common.dtos.ApiResponse;
import com.gerardo.thrive.user.dtos.request.UserLoginRequestDto;
import com.gerardo.thrive.user.dtos.request.UserRequestDto;
import com.gerardo.thrive.user.dtos.response.UserResponseDto;
import com.gerardo.thrive.user.services.CustomUserDetailsService;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/security/user/test")
@RequiredArgsConstructor
public class UserController {
    private final CustomUserDetailsService customUserDetailsService;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> registerUser(@Valid @RequestBody UserRequestDto request) {
        UserResponseDto user = customUserDetailsService.registerUser(request);
        ApiResponse<UserResponseDto> response = ApiResponse.created(user, "USER_CREATED");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponseDto>> login(@Valid @RequestBody UserLoginRequestDto request) {
        UserResponseDto user = customUserDetailsService.loginUser(request);
        ApiResponse<UserResponseDto> response = ApiResponse.created(user, "USER_LOGIN");
        return ResponseEntity.ok(response);
    }
}
