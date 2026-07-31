package com.gerardo.thrive.user.mappers;

import com.gerardo.thrive.user.dtos.request.UserRequestDto;
import com.gerardo.thrive.user.dtos.response.UserResponseDto;
import com.gerardo.thrive.user.entities.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserModel toModel(UserRequestDto request) {
        if (request == null) return null;

        UserModel model = new UserModel();
        model.setEmail(request.email());
        model.setName(request.name());
        model.setUsername(request.username());


        return model;
    }

    public UserResponseDto toResponse(UserModel userModel) {
        if (userModel == null) return null;
        return new UserResponseDto(
                userModel.getId(),
                userModel.getName(),
                userModel.getUsername(),
                userModel.getEmail()
        );
    }
}
