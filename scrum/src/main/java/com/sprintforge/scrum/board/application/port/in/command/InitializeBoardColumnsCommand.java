package com.sprintforge.scrum.board.application.port.in.command;

import java.util.UUID;

public record InitializeBoardColumnsCommand(
        UUID sprintId
) {
}
