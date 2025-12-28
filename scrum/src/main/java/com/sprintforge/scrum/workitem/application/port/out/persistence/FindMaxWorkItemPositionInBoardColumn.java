package com.sprintforge.scrum.workitem.application.port.out.persistence;

import java.util.Optional;
import java.util.UUID;

public interface FindMaxWorkItemPositionInBoardColumn {
    Optional<Integer> findMaxPosition(UUID projectId, UUID sprintId, UUID boardColumnId);
}
