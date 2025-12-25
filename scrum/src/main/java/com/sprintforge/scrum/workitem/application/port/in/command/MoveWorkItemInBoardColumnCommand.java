package com.sprintforge.scrum.workitem.application.port.in.command;

import java.util.UUID;

public record MoveWorkItemInBoardColumnCommand(
        UUID employeeId,
        UUID id,
        int newPosition
) {
}
