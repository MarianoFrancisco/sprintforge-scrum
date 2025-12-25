package com.sprintforge.scrum.workitem.application.port.out.persistence;

import java.util.UUID;

public interface FindNextBoardColumnPosition {
    int findNextPosition(UUID projectId, UUID sprintId, UUID columnId);
}
