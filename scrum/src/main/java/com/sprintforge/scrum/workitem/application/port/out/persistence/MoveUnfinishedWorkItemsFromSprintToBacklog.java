package com.sprintforge.scrum.workitem.application.port.out.persistence;

import java.util.UUID;

public interface MoveUnfinishedWorkItemsFromSprintToBacklog {
    void moveAll(UUID projectId,
                 UUID fromCompletedSprintId,
                 int startBacklogPosition);
}
