package com.sprintforge.scrum.project.application.port.out.persistence.raw;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public record ProjectRawItem(
        UUID projectId,
        String projectKey,
        String projectName,

        long backlogCount,
        long pendingCount,
        long inProgressCount,
        long completedCount,

        long totalSprints,
        long startedSprints,
        long createdSprints,
        long completedSprints,

        List<StartedSprintRaw> startedSprintsRaw,

        Set<UUID> memberEmployeeIds
) {
}
