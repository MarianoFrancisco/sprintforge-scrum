package com.sprintforge.scrum.board.application.port.in.command;

import java.util.UUID;

public record CreateBoardColumnCommand(
        UUID employeeId,
        UUID sprintId,
        String name
) {
}
