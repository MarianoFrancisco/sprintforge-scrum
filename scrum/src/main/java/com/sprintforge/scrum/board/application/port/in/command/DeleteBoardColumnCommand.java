package com.sprintforge.scrum.board.application.port.in.command;

import java.util.UUID;

public record DeleteBoardColumnCommand(
        UUID employeeId,
        UUID id,
        UUID targetBoardColumnId
) {
}
