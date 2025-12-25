package com.sprintforge.scrum.workitem.application.port.out.persistence;

import java.util.UUID;

public interface MoveWorkItemsFromSprintToBacklog {
    void moveAll(UUID projectId, UUID sprintId, int startBacklogPosition);
}
