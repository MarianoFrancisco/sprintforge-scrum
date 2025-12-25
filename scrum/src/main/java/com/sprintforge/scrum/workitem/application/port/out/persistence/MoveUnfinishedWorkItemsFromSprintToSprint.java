package com.sprintforge.scrum.workitem.application.port.out.persistence;

import java.util.UUID;

public interface MoveUnfinishedWorkItemsFromSprintToSprint {
    void moveAll(UUID projectId,
                 UUID fromCompletedSprintId,
                 UUID toSprintId,
                 UUID toBoardColumnId,
                 int startPositionInTargetColumn);
}
