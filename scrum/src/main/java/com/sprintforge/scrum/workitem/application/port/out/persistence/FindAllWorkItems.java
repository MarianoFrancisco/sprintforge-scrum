package com.sprintforge.scrum.workitem.application.port.out.persistence;

import com.sprintforge.scrum.workitem.application.port.in.query.GetAllWorkItemsQuery;
import com.sprintforge.scrum.workitem.domain.WorkItem;

import java.util.List;

public interface FindAllWorkItems {
    List<WorkItem> findAll(GetAllWorkItemsQuery query);
}
