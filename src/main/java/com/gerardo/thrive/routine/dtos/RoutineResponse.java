package com.gerardo.thrive.routine.dtos;

import lombok.Builder;

@Builder
public record RoutineResponse(
        Long id,
        String name,
        String description,
        Long user_id
) {
}
