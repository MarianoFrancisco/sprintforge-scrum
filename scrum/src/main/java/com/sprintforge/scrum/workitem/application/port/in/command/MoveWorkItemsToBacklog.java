package com.sprintforge.scrum.workitem.application.port.in.command;

public interface MoveWorkItemsToBacklog {
    void handle(MoveWorkItemsToBacklogCommand command);
}
