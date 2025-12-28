package com.sprintforge.scrum.board.application.port.out.persistence;

import java.util.UUID;

public interface BoardColumnExistsForSprint {
    boolean existsActiveBySprintId(UUID sprintId);
}
