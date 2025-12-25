package com.sprintforge.scrum.workitem.application.port.out.persistence;

import com.sprintforge.scrum.workitem.domain.WorkItem;

public interface SaveWorkItem {
    WorkItem save(WorkItem workItem);
}
