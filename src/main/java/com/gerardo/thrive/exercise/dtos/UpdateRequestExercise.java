package com.gerardo.thrive.exercise.dtos;


import com.gerardo.thrive.exercise.enums.MuscleGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record
UpdateRequestExercise(
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "Description is required") String description,
        @NotBlank(message = "Image URI is required") String imageUri,
        @NotNull(message = "Muscle group is required") MuscleGroup muscleGroup
) {
}
