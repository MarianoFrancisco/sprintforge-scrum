package com.sprintforge.scrum.workitem.application.port.in.query;

import com.sprintforge.scrum.workitem.domain.WorkItem;

public interface GetWorkItemById {
    WorkItem handle(GetWorkItemByIdQuery query);
}
