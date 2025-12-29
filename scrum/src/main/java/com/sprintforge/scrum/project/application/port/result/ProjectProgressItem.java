package com.sprintforge.scrum.project.application.port.result;

import com.sprintforge.scrum.common.application.port.result.EmployeeResult;

import java.util.List;
import java.util.UUID;

public record ProjectProgressItem(
        UUID projectId,
        String projectKey,
        String projectName,

        long backlogCount,
        long pendingCount,
        long inProgressCount,
        long completedCount,
        long totalStories,

        double progressPercentage,

        long totalSprints,
        long startedSprints,
        long createdSprints,
        long completedSprints,

        List<SprintCurrent> currentSprints,
        List<EmployeeResult> members
) {
}
