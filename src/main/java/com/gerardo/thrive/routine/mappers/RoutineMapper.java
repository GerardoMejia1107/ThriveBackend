package com.gerardo.thrive.routine.mappers;

import com.gerardo.thrive.routine.dtos.CreateRoutineRequest;
import com.gerardo.thrive.routine.dtos.RoutineResponse;
import com.gerardo.thrive.routine.entities.RoutineModel;
import com.gerardo.thrive.user.entities.UserModel;
import org.springframework.stereotype.Component;

@Component
public class RoutineMapper {
    public RoutineModel toModel(CreateRoutineRequest request, UserModel user) {
        if (request == null) return null;
        RoutineModel model = new RoutineModel();
        model.setName(request.name());
        model.setDescription(request.description());
        model.setUser(user);

        return model;
    }

    public RoutineResponse toResponse(RoutineModel model) {
        if (model == null) return null;
        return RoutineResponse.builder()
                .name(model.getName())
                .description(model.getDescription())
                .user_id(model.getUser()
                        .getId())
                .build();
    }

}
