package com.sprintforge.scrum.board.application.port.in.command;

import java.util.UUID;

public record UpdateBoardColumnNameCommand(
        UUID employeeId,
        UUID id,
        String name
) {
}
