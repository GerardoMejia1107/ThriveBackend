package com.gerardo.thrive.session.entities;

import com.gerardo.thrive.common.BaseEntity;
import com.gerardo.thrive.exercise.entities.ExerciseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "session_exercises")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionExerciseModel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private SessionModel session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private ExerciseModel exercise;

    @Column(nullable = false, name = "order_position")
    private Integer orderPosition;
}
