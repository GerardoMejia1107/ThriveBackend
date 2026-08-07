package com.gerardo.thrive.auth.services;

import com.gerardo.thrive.auth.dtos.request.LoginRequestDto;
import com.gerardo.thrive.auth.dtos.request.RegisterRequestDto;
import com.gerardo.thrive.auth.repositories.RefreshTokenRepository;
import com.gerardo.thrive.auth.security.CustomPasswordEncoder;
import com.gerardo.thrive.user.dtos.response.UserResponseDto;
import com.gerardo.thrive.user.entities.UserModel;
import com.gerardo.thrive.user.mappers.UserMapper;
import com.gerardo.thrive.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final CustomPasswordEncoder customPasswordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    /*Sing up method*/
    public UserResponseDto registerUser(RegisterRequestDto request) {
        String hash = customPasswordEncoder.encode(request.password());
        UserModel newUser = userRepository.save(userMapper.toModel(request, hash));

        UserDetails userJustCreatedDetails = customUserDetailsService.loadUserByUsername(request.email());

        return userMapper.toResponse(newUser, jwtService.generateToken(userJustCreatedDetails));
    }

    /*Login Method*/
    public UserResponseDto loginUser(LoginRequestDto request) {
        UserDetails userByUsername = customUserDetailsService.loadUserByUsername(request.email());

        if (!customPasswordEncoder.matches(request.password(), userByUsername.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        UserModel userRepositoryByEmail = userRepository.findByEmail(request.email());

        return UserResponseDto.builder()
                .id(userRepositoryByEmail.getId())
                .email(userByUsername.getUsername())
                .name(userRepositoryByEmail.getName())
                .username(userByUsername.getUsername())
                .role(userRepositoryByEmail.getRole())
                .token(jwtService.generateToken(userByUsername))
                .build();
    }
}
