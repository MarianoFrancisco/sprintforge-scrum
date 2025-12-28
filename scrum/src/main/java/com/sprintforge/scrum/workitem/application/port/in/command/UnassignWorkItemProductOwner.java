package com.sprintforge.scrum.workitem.application.port.in.command;

import com.sprintforge.scrum.workitem.domain.WorkItem;

public interface UnassignWorkItemProductOwner {
    WorkItem handle(UnassignWorkItemProductOwnerCommand command);
}
