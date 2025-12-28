package com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "project_employee")
public class ProjectEmployeeEntity {

    @EmbeddedId
    private ProjectEmployeeId id;

    @MapsId("projectId")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @Column(name = "assigned_at", nullable = false)
    private Instant assignedAt;
}
