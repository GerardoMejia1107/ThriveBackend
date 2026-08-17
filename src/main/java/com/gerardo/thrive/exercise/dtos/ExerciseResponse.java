package com.gerardo.thrive.exercise.dtos;

import com.gerardo.thrive.exercise.enums.MuscleGroup;
import lombok.Builder;

@Builder
public record ExerciseResponse(
        Long id, String name, String description, String uri, MuscleGroup muscleGroup
) {
}
