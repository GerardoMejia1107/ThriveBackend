package com.gerardo.thrive.user.services;

import com.gerardo.thrive.user.dtos.response.UserResponseDto;
import com.gerardo.thrive.user.entities.UserModel;
import com.gerardo.thrive.user.mappers.UserMapper;
import com.gerardo.thrive.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDto findUserById(Long id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return userMapper.toResponse(user, null);
    }
}
