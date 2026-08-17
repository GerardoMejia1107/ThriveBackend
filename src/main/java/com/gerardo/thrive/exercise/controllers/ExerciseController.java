package com.gerardo.thrive.exercise.controllers;

import com.gerardo.thrive.common.dtos.ApiResponse;
import com.gerardo.thrive.exercise.dtos.CreateExerciseRequest;
import com.gerardo.thrive.exercise.dtos.ExerciseResponse;
import com.gerardo.thrive.exercise.dtos.UpdateRequestExercise;
import com.gerardo.thrive.exercise.services.ExerciseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thrive/exercises")
@RequiredArgsConstructor
public class ExerciseController {
    private final ExerciseService service;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<ExerciseResponse>> createExercise(
            @Valid @RequestBody CreateExerciseRequest request) {
        ExerciseResponse exerciseResponse = service.create(request);
        ApiResponse<ExerciseResponse> apiResponse = ApiResponse.created(exerciseResponse,
                "Exercise '%s' created successfully" .formatted(exerciseResponse.name()));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(apiResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ExerciseResponse>>> findAllExercises() {
        List<ExerciseResponse> exerciseResponse = service.list();
        ApiResponse<List<ExerciseResponse>> response = ApiResponse.ok(exerciseResponse,
                "Retrieved %d exercise(s)" .formatted(exerciseResponse.size()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/searchBy/{id}")
    public ResponseEntity<ApiResponse<ExerciseResponse>> findExerciseById(@PathVariable Long id) {
        ExerciseResponse exerciseResponse = service.listById(id);
        ApiResponse<ExerciseResponse> response = ApiResponse.ok(exerciseResponse,
                "Exercise '%s' retrieved successfully" .formatted(exerciseResponse.name()));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/fullUpdate/{id}")
    public ResponseEntity<ApiResponse<ExerciseResponse>> updateExerciseById(@PathVariable Long id, @Valid @RequestBody
    UpdateRequestExercise updateRequestExercise) {
        ExerciseResponse exerciseResponse = service.updateById(id, updateRequestExercise);
        ApiResponse<ExerciseResponse> response = ApiResponse.updated(exerciseResponse,
                "Exercise '%s' updated successfully".formatted(exerciseResponse.name()));
        return ResponseEntity.ok(response);
    }
}   
