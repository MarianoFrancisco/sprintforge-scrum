package com.sprintforge.scrum.workitem.application.port.out.persistence;

import com.sprintforge.scrum.workitem.domain.WorkItem;

import java.util.Optional;
import java.util.UUID;

public interface FindWorkItemById {
    Optional<WorkItem> findById(UUID id);
}
