package com.gerardo.thrive.routine.entities;

import com.gerardo.thrive.common.enums.WeightUnit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "routine_sets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoutineSetModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_and_exercise_id", nullable = false)
    private RoutineExerciseModel routineAndExercise;

    @Column(nullable = false, name = "set_number")
    private Integer setNumber;

    private String notes;

    @Column(nullable = false, name = "target_repetitions")
    private Integer targetRepetitions;

    @Column(nullable = false, name = "target_weight")
    private Double targetWeight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "target_weight_unit")
    private WeightUnit targetWeightUnit;

    @Column(nullable = false, name = "target_rest_seconds")
    private Long targetRestSeconds;
}

