package com.gerardo.thrive.session.entities;


import com.gerardo.thrive.common.BaseEntity;
import com.gerardo.thrive.routine.entities.RoutineModel;
import com.gerardo.thrive.user.entities.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import java.time.LocalDateTime;

@Entity
@Table(name = "sessions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionModel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id")
    private RoutineModel routine;


    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false, name = "duration_seconds")
    private Long durationSeconds;

    @Column(nullable = false)
    private boolean completed;

    private String notes;
}
