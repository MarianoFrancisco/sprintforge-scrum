package com.sprintforge.scrum.workitem.application.port.out.persistence;

import java.util.UUID;

public interface MoveWorkItemsFromBoardColumnToAnother {
    void moveAll(UUID projectId, UUID sprintId, UUID fromBoardColumnId, UUID toBoardColumnId, int startPositionInTarget);
}
