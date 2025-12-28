package com.sprintforge.scrum.workitem.application.port.out.persistence;

import java.util.UUID;

public interface ShiftWorkItemPositionsAfterDeletionInBacklog {
    void shiftLeft(UUID projectId, int deletedPosition);
}
