package com.gerardo.thrive.exercise.repositories;

import com.gerardo.thrive.exercise.entities.ExerciseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<ExerciseModel, Long> {
    boolean existsByName(String name);
}
