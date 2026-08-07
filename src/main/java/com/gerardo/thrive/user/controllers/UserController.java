package com.gerardo.thrive.user.controllers;

import com.gerardo.thrive.common.dtos.ApiResponse;
import com.gerardo.thrive.user.dtos.response.UserResponseDto;
import com.gerardo.thrive.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> findUserById(@PathVariable Long id) {
        UserResponseDto user = userService.findUserById(id);
        ApiResponse<UserResponseDto> response = ApiResponse.ok(user, "USER_FOUND");

        return ResponseEntity.ok(response);
    }
}
