package com.sprintforge.scrum.workitem.application.port.in.command;

import java.util.UUID;

public record MoveWorkItemsToBacklogAfterSprintDeletionCommand(
        UUID employeeId,
        UUID projectId,
        UUID sprintId
) {
}
