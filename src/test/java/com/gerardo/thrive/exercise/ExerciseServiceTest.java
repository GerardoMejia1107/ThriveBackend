package com.gerardo.thrive.exercise;

import com.gerardo.thrive.exercise.dtos.CreateExerciseRequest;
import com.gerardo.thrive.exercise.dtos.ExerciseResponse;
import com.gerardo.thrive.exercise.entities.ExerciseModel;
import com.gerardo.thrive.exercise.enums.MuscleGroup;
import com.gerardo.thrive.exercise.mappers.ExerciseMapper;
import com.gerardo.thrive.exercise.repositories.ExerciseRepository;
import com.gerardo.thrive.exercise.services.ExerciseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExerciseServiceTest {
    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private ExerciseMapper exerciseMapper;

    @InjectMocks
    private ExerciseService exerciseService;

    @Test
    void create_shouldReturnSavedExercise_whenValidRequest() {
        //ARRANGE
        CreateExerciseRequest createExerciseRequest = new CreateExerciseRequest(
                "Barbell Bench Press",
                "A compound push exercise targeting the chest, performed lying on a bench.",
                "https://example.com/images/barbell-bench-press.jpg",
                MuscleGroup.CHEST
        );
        ExerciseModel modelToSave = new ExerciseModel();
        ExerciseModel savedModel = new ExerciseModel();
        savedModel.setId(1L);
        ExerciseResponse expectedResponse = new ExerciseResponse(savedModel.getId(), createExerciseRequest.name(),
                createExerciseRequest.description(), createExerciseRequest.imageUri(),
                createExerciseRequest.muscleGroup());

        when(exerciseRepository.existsByName(createExerciseRequest.name()))
                .thenReturn(false);
        when(exerciseMapper.toModel(createExerciseRequest)).thenReturn(modelToSave);
        when(exerciseRepository.save(modelToSave)).thenReturn(savedModel);
        when(exerciseMapper.toResponse(savedModel)).thenReturn(expectedResponse);

        //ACT
        ExerciseResponse response = exerciseService.create(createExerciseRequest);

        //ASSERT
        assertEquals(expectedResponse, response);
        verify(exerciseRepository).save(modelToSave);
    }

    @Test
    void create_shouldReturnException_whenModelAlreadyExists() {
        //ARRANGE
        CreateExerciseRequest createExerciseRequest = new CreateExerciseRequest(
                "Barbell Bench Press",
                "A compound push exercise targeting the chest, performed lying on a bench.",
                "https://example.com/images/barbell-bench-press.jpg",
                MuscleGroup.CHEST
        );
        when(exerciseRepository.existsByName(createExerciseRequest.name())).thenReturn(true);

        //ASSERT
        assertThrows(RuntimeException.class, () -> exerciseService.create(createExerciseRequest));
        verify(exerciseRepository, never()).save(any());
    }

    @Test
    void get_shouldReturnList_whenValid() {
        ExerciseModel model1 = new ExerciseModel();
        ExerciseModel model2 = new ExerciseModel();
        ExerciseResponse response1 = new ExerciseResponse(
                1L,
                "Bench Press",
                "A compound push exercise targeting the chest, performed lying on a bench.",
                "https://example.com/images/bench-press.jpg",
                MuscleGroup.CHEST
        );
        ExerciseResponse response2 = new ExerciseResponse(
                2L,
                "Barbell Row",
                "A compound pull exercise targeting the back, performed bent over with a barbell.",
                "https://example.com/images/barbell-row.jpg",
                MuscleGroup.BACK
        );
        when(exerciseRepository.findAll()).thenReturn(List.of(model1, model2));
        when(exerciseMapper.toResponse(model1)).thenReturn(response1);
        when(exerciseMapper.toResponse(model2)).thenReturn(response2);

        List<ExerciseResponse> result = exerciseService.list();
        assertEquals(List.of(response1, response2), result);
    }
}
