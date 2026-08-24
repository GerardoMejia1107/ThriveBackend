package com.gerardo.thrive.routine.repositories;

import com.gerardo.thrive.routine.entities.RoutineModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutineRepository extends JpaRepository<RoutineModel, Long> {
    boolean existsByName(String name);
}
