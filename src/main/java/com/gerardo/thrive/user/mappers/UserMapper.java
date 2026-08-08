package com.gerardo.thrive.user.mappers;

import com.gerardo.thrive.auth.dtos.request.RegisterRequestDto;
import com.gerardo.thrive.user.dtos.response.UserResponseDto;
import com.gerardo.thrive.user.entities.UserModel;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserModel toModel(RegisterRequestDto request, String hash) {
        if (request == null) return null;

        UserModel model = new UserModel();
        model.setEmail(request.email());
        model.setName(request.name());
        model.setUsername(request.username());
        model.setPassword_hash(hash);


        return model;
    }

    public UserResponseDto toResponse(UserModel userModel, String token, String refreshToken) {
        if (userModel == null) return null;
        return new UserResponseDto(
                userModel.getId(),
                userModel.getName(),
                userModel.getUsername(),
                userModel.getEmail(),
                userModel.getRole(),
                token,
                refreshToken


        );
    }
}
