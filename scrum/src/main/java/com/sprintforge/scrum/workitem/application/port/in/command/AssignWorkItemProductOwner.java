package com.sprintforge.scrum.workitem.application.port.in.command;

import com.sprintforge.scrum.workitem.domain.WorkItem;

public interface AssignWorkItemProductOwner {
    WorkItem handle(AssignWorkItemProductOwnerCommand command);
}
