package com.gerardo.thrive.routine.entities;

import com.gerardo.thrive.common.BaseEntity;
import com.gerardo.thrive.user.entities.UserModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "routines")
@Setter
@Getter
public class RoutineModel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;
}
