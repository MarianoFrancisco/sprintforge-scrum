package com.sprintforge.scrum.workitem.application.port.in.query;

import com.sprintforge.scrum.workitem.application.result.WorkItemResult;

public interface GetWorkItemResultById {
    WorkItemResult handle(GetWorkItemResultByIdQuery query);
}
