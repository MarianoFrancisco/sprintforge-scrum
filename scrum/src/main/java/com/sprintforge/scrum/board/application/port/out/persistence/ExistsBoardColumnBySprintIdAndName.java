package com.sprintforge.scrum.board.application.port.out.persistence;

import java.util.UUID;

public interface ExistsBoardColumnBySprintIdAndName {
    boolean existsBySprintIdAndName(UUID sprintId, String name);
}
