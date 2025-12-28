package com.sprintforge.scrum.workitem.application.port.in.command;

import java.util.UUID;

public record ReassignWorkItemsAfterSprintCompletionCommand(
        UUID employeeId,
        UUID projectId,
        UUID completedSprintId,
        UUID targetSprintId
) {
}
