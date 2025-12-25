package com.sprintforge.scrum.workitem.application.port.in.command;

public interface MoveWorkItemsToBacklogAfterSprintDeletion {
    void handle(MoveWorkItemsToBacklogAfterSprintDeletionCommand command);
}
