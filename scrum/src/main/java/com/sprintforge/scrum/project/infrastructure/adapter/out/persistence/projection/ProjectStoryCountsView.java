package com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.projection;

import java.util.UUID;

public interface ProjectStoryCountsView {
    UUID getProjectId();

    String getProjectKey();

    String getProjectName();

    long getBacklogCount();

    long getPendingCount();

    long getInProgressCount();

    long getCompletedCount();
}