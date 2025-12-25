package com.sprintforge.scrum.workitem.infrastructure.adapter.out.persistence.entity;

import com.sprintforge.scrum.board.infrastructure.adapter.out.persistence.entity.BoardColumnEntity;
import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.entity.ProjectEntity;
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
@Table(name = "work_item")
public class WorkItemEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sprint_id")
    private SprintEntity sprint;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "column_id")
    private BoardColumnEntity boardColumn;

    @Column(name = "position", nullable = false)
    private int position;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "description", nullable = false, length = 2000)
    private String description;

    @Column(name = "acceptance_criteria", nullable = false, length = 2000)
    private String acceptanceCriteria;

    @Column(name = "story_points")
    private Integer storyPoints;

    @Column(name = "priority", nullable = false)
    private int priority;

    @Column(name = "developer_id")
    private UUID developerId;

    @Column(name = "product_owner_id")
    private UUID productOwnerId;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
