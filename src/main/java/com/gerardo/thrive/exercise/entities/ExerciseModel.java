package com.gerardo.thrive.exercise.entities;

import com.gerardo.thrive.common.BaseEntity;
import com.gerardo.thrive.exercise.enums.MuscleGroup;
import jakarta.persistence.*;

@Entity
@Table(name = "exercises")
public class ExerciseModel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, name = "image_uri")
    private String imageUri;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "muscle_group")
    private MuscleGroup muscleGroup;
}
