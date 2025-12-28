package com.sprintforge.scrum.workitem.application.port.in.command;

public interface ReassignWorkItemsAfterBoardColumnDeletion {
    void handle(ReassignWorkItemsAfterBoardColumnDeletionCommand command);
}
