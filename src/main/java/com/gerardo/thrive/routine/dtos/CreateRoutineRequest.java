package com.gerardo.thrive.routine.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateRoutineRequest(
        @NotNull Long user_id,
        @NotBlank String name,
        @NotBlank String description
) {
}
