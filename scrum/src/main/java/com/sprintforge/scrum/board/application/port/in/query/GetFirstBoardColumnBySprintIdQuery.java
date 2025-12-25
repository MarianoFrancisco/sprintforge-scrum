package com.sprintforge.scrum.board.application.port.in.query;

import java.util.UUID;

public record GetFirstBoardColumnBySprintIdQuery(
        UUID sprintId
) {
}
