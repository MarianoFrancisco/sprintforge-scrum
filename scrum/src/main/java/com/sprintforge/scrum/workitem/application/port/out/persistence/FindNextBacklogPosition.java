package com.sprintforge.scrum.workitem.application.port.out.persistence;

import java.util.UUID;

public interface FindNextBacklogPosition {
    int findNextPosition(UUID projectId);
}
