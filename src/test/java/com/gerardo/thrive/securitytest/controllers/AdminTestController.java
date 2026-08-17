package com.gerardo.thrive.securitytest.controllers;

import com.gerardo.thrive.common.dtos.ApiResponse;
import com.gerardo.thrive.securitytest.dtos.response.SecurityTestResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/security/resource/test")
public class AdminTestController {

    @GetMapping("/data")
    public ResponseEntity<ApiResponse<SecurityTestResponseDto>> getData(Authentication authentication) {
        List<String> authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        SecurityTestResponseDto dto = new SecurityTestResponseDto(
                authentication.getName(), authorities, "ADMIN_RESOURCE_ACCESSED");
        return ResponseEntity.ok(ApiResponse.ok(dto, "ADMIN_RESOURCE_ACCESSED"));
    }
}
