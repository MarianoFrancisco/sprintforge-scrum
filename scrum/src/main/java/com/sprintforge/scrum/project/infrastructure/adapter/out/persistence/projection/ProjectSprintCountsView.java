package com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.projection;

import java.util.UUID;

public interface ProjectSprintCountsView {
    UUID getProjectId();

    long getTotalSprints();

    long getStartedSprints();

    long getCreatedSprints();

    long getCompletedSprints();
}
