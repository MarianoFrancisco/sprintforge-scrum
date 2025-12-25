package com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "project")
public class ProjectEntity {
    @Id
    private UUID id;

    @Column(name = "project_key", nullable = false, length = 10, unique = true)
    private String projectKey;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(length = 100, nullable = false)
    private String client;

    @Column(length = 80, nullable = false)
    private String area;

    @Column(name = "budget_amount", precision = 12, scale = 2)
    private BigDecimal budgetAmount;

    @Column(name = "contract_amount", precision = 12, scale = 2)
    private BigDecimal contractAmount;

    @Column(name = "is_closed", nullable = false)
    private boolean isClosed;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private Set<ProjectEmployeeEntity> employees = new HashSet<>();
}
