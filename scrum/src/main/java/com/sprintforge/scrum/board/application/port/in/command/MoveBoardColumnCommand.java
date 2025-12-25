package com.sprintforge.scrum.board.application.port.in.command;

import java.util.UUID;

public record MoveBoardColumnCommand(
        UUID employeeId,
        UUID id,
        int newPosition
) {
}
