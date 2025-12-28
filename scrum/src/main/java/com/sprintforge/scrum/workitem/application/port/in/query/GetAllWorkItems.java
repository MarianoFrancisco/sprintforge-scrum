package com.sprintforge.scrum.workitem.application.port.in.query;

import com.sprintforge.scrum.workitem.domain.WorkItem;

import java.util.List;

public interface GetAllWorkItems {
    List<WorkItem> handle(GetAllWorkItemsQuery query);
}
