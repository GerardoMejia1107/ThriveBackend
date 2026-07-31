package com.gerardo.thrive.routine.entities;


import com.gerardo.thrive.common.BaseEntity;
import com.gerardo.thrive.exercise.entities.ExerciseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "routine_exercises")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoutineExerciseModel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    * nullable false was set on both relations because 1 row in this entity has no
    * sense if one of the engaged entities is  null. One row here means a particular exercise
    * belongs to a specific routine.
    * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id", nullable = false)
    private RoutineModel routine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private ExerciseModel exercise;

    @Column(nullable = false, name = "order_position")
    private Integer order_position;
}
