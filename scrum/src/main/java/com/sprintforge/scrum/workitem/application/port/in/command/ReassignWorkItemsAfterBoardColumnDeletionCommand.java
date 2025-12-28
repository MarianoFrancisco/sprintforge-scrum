package com.sprintforge.scrum.workitem.application.port.in.command;

import java.util.UUID;

public record ReassignWorkItemsAfterBoardColumnDeletionCommand(
        UUID employeeId,
        UUID projectId,
        UUID sprintId,
        UUID deletedBoardColumnId,
        UUID fallbackBoardColumnId
) {
}
