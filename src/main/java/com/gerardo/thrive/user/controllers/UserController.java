package com.gerardo.thrive.user.controllers;

import com.gerardo.thrive.user.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/security/user/test")
@RequiredArgsConstructor
public class UserController {
    private final CustomUserDetailsService  customUserDetailsService;
}
