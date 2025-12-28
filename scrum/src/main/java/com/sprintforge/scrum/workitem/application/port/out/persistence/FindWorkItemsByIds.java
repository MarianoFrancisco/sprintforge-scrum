package com.sprintforge.scrum.workitem.application.port.out.persistence;

import com.sprintforge.scrum.workitem.domain.WorkItem;

import java.util.List;
import java.util.UUID;

public interface FindWorkItemsByIds {
    List<WorkItem> findAllByIds(List<UUID> ids);
}
