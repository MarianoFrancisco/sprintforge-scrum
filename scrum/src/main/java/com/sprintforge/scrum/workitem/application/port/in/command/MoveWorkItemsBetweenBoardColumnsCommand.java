package com.sprintforge.scrum.workitem.application.port.in.command;

import java.util.List;
import java.util.UUID;

public record MoveWorkItemsBetweenBoardColumnsCommand(
        UUID employeeId,
        UUID sprintId,
        UUID targetBoardColumnId,
        int targetPosition,
        List<UUID> ids
) {
}
