package com.gerardo.thrive.exercise.mappers;

import com.gerardo.thrive.exercise.dtos.CreateExerciseRequest;
import com.gerardo.thrive.exercise.dtos.ExerciseResponse;
import com.gerardo.thrive.exercise.entities.ExerciseModel;
import org.springframework.stereotype.Component;

@Component
public class ExerciseMapper {
    public ExerciseModel toModel(CreateExerciseRequest request) {
        if (request == null) return null;
        ExerciseModel model = new ExerciseModel();
        model.setName(request.name());
        model.setDescription(request.description());
        model.setImageUri(request.imageUri());
        model.setMuscleGroup(request.muscleGroup());

        return model;
    }

    public ExerciseResponse toResponse(ExerciseModel model) {
        if (model == null) return null;
        return ExerciseResponse.builder()
                .name(model.getName())
                .description(model.getDescription())
                .uri(model.getImageUri())
                .muscleGroup(model.getMuscleGroup())
                .build();
    }

}
