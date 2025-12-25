package com.sprintforge.scrum.workitem.application.port.out.persistence;

import java.util.UUID;

public interface ShiftWorkItemPositionsInBoardColumn {

    void shiftUp(UUID projectId, UUID sprintId, UUID boardColumnId, int fromInclusive, int toExclusive);

    void shiftDown(UUID projectId, UUID sprintId, UUID boardColumnId, int fromExclusive, int toInclusive);
}
