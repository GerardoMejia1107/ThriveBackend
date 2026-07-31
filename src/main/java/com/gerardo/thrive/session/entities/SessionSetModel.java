package com.gerardo.thrive.session.entities;

import com.gerardo.thrive.common.BaseEntity;
import com.gerardo.thrive.common.enums.WeightUnit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "session_sets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionSetModel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_and_exercise_id",  nullable = false)
    private SessionExerciseModel sessionAndExercise;

    @Column(nullable = false, name = "set_number")
    private Integer setNumber;

    private String notes;

    @Column(nullable = false, name = "effective_repetitions")
    private Integer effectiveRepetitions;

    @Column(nullable = false, name = "effective_weight")
    private Double effectiveWeight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "effective_weight_unit")
    private WeightUnit effectiveWeightUnit;

    @Column(nullable = false, name = "effective_rest_time")
    private Long effectiveRestTime;

    @Column(nullable = false)
    private boolean completed;

}
