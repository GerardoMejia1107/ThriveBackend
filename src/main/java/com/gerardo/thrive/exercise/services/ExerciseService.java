package com.gerardo.thrive.exercise.services;

import com.gerardo.thrive.exercise.dtos.CreateExerciseRequest;
import com.gerardo.thrive.exercise.dtos.ExerciseResponse;
import com.gerardo.thrive.exercise.dtos.UpdateRequestExercise;
import com.gerardo.thrive.exercise.entities.ExerciseModel;
import com.gerardo.thrive.exercise.mappers.ExerciseMapper;
import com.gerardo.thrive.exercise.repositories.ExerciseRepository;
import com.gerardo.thrive.user.entities.UserModel;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository repository;
    private final ExerciseMapper mapper;

    public ExerciseResponse create(CreateExerciseRequest request) {
        if (repository.existsByName(request.name())) {
            throw new RuntimeException();
        }
        ExerciseModel newExercise = repository.save(mapper.toModel(request));
        return mapper.toResponse(newExercise);
    }

    public List<ExerciseResponse> list() {
        List<ExerciseModel> list = repository.findAll();

        return list.stream()
                .map(mapper::toResponse)
                .toList();
    }

    public ExerciseResponse listById(Long id) {
        ExerciseModel exerciseModel = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return mapper.toResponse(exerciseModel);
    }

    public ExerciseResponse updateById(Long id, UpdateRequestExercise updateRequestExercise) {
        ExerciseModel existing = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        existing.setName(updateRequestExercise.name());
        existing.setDescription(updateRequestExercise.description());
        existing.setImageUri(updateRequestExercise.imageUri());
        existing.setMuscleGroup(updateRequestExercise.muscleGroup());

        return mapper.toResponse(repository.save(existing));
    }

    public ExerciseResponse deleteById(Long id) {
        ExerciseModel model = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        repository.deleteById(id);
        return mapper.toResponse(model);
    }

}
