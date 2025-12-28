package com.sprintforge.scrum.workitem.application.port.out.persistence;

import java.util.UUID;

public interface ShiftWorkItemPositionsAfterInsertionInBoardColumn {
    void shiftRight(UUID projectId, UUID sprintId, UUID boardColumnId, int fromInclusive, int delta);
}
