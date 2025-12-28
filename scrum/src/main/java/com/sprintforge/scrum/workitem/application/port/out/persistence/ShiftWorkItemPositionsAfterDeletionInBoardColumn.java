package com.sprintforge.scrum.workitem.application.port.out.persistence;

import java.util.UUID;

public interface ShiftWorkItemPositionsAfterDeletionInBoardColumn {
    void shiftLeft(UUID projectId, UUID sprintId, UUID columnId, int deletedPosition);
}
