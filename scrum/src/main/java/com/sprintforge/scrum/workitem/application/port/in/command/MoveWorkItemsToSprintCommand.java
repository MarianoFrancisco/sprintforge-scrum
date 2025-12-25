package com.sprintforge.scrum.workitem.application.port.in.command;

import java.util.List;
import java.util.UUID;

public record MoveWorkItemsToSprintCommand(
        UUID employeeId,
        List<UUID> ids,
        UUID sprintId
) {
}
