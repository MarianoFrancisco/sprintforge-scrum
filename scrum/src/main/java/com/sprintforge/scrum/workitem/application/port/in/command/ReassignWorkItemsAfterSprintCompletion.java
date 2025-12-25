package com.sprintforge.scrum.workitem.application.port.in.command;

public interface ReassignWorkItemsAfterSprintCompletion {
    void handle(ReassignWorkItemsAfterSprintCompletionCommand command);
}
