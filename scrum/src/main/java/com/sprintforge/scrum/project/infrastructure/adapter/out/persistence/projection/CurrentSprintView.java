package com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.projection;

import java.time.Instant;
import java.util.UUID;

public interface CurrentSprintView {
    UUID getProjectId();

    UUID getSprintId();

    String getName();

    String getGoal();

    String getStatus();

    Instant getStartDate();

    Instant getEndDate();
}
