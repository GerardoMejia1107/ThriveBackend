package com.gerardo.thrive.routine.services;

import com.gerardo.thrive.routine.dtos.CreateRoutineRequest;
import com.gerardo.thrive.routine.dtos.RoutineResponse;
import com.gerardo.thrive.routine.entities.RoutineModel;
import com.gerardo.thrive.routine.mappers.RoutineMapper;
import com.gerardo.thrive.routine.repositories.RoutineRepository;
import com.gerardo.thrive.user.entities.UserModel;
import com.gerardo.thrive.user.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoutineService {
    private final RoutineRepository routineRepository;
    private final UserRepository userRepository;
    private final RoutineMapper mapper;

    public RoutineResponse create(CreateRoutineRequest request) {
        UserModel existingUser = userRepository.findById(request.user_id())
                .orElseThrow(EntityNotFoundException::new);

        if (routineRepository.existsByName(request.name())) {
            throw new RuntimeException();
        }
        RoutineModel newRoutine = routineRepository.save(mapper.toModel(request, existingUser));

        return mapper.toResponse(newRoutine);
    }
}
