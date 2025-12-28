package com.sprintforge.scrum.workitem.application.port.in.command;

import java.util.UUID;

public record CreateWorkItemCommand(
        UUID employeeId,
        UUID projectId,
        UUID sprintId,
        UUID boardColumnId,
        String title,
        String description,
        String acceptanceCriteria,
        Integer storyPoints,
        int priority,
        UUID developerId,
        UUID productOwnerId
) {
}
