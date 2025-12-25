package com.sprintforge.scrum.workitem.application.port.in.command;

import java.util.List;
import java.util.UUID;

public record MoveWorkItemsToBacklogCommand(
        UUID employeeId,
        List<UUID> ids
) {
}
