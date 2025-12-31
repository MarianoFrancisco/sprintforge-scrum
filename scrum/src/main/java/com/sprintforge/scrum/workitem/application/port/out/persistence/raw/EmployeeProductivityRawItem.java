package com.sprintforge.scrum.workitem.application.port.out.persistence.raw;

import java.util.UUID;

public record EmployeeProductivityRawItem(
        UUID employeeId,
        long workedStories,
        long completedStories,
        long pendingStories,
        long deliveredStoryPoints
) {
}
