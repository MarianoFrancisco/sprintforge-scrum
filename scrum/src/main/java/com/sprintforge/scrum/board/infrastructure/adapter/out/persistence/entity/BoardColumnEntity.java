package com.sprintforge.scrum.board.infrastructure.adapter.out.persistence.entity;

import com.sprintforge.scrum.sprint.infrastructure.adapter.out.persistence.entity.SprintEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "board_column")
public class BoardColumnEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sprint_id", nullable = false)
    private SprintEntity sprint;

    @Column(name = "name", nullable = false, length = 60)
    private String name;

    @Column(name = "position", nullable = false)
    private int position;

    @Column(name = "is_final", nullable = false)
    private boolean isFinal;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}