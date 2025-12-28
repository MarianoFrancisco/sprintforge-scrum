package com.sprintforge.scrum.workitem.application.service;

import com.sprintforge.scrum.workitem.application.port.in.command.MoveWorkItemsToBacklogAfterSprintDeletion;
import com.sprintforge.scrum.workitem.application.port.in.command.MoveWorkItemsToBacklogAfterSprintDeletionCommand;
import com.sprintforge.scrum.workitem.application.port.out.persistence.FindNextBacklogPosition;
import com.sprintforge.scrum.workitem.application.port.out.persistence.MoveWorkItemsFromSprintToBacklog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MoveWorkItemsToBacklogAfterSprintDeletionImpl
        implements MoveWorkItemsToBacklogAfterSprintDeletion {

    private final FindNextBacklogPosition findNextBacklogPosition;
    private final MoveWorkItemsFromSprintToBacklog moveWorkItemsFromSprintToBacklog;

    @Override
    public void handle(MoveWorkItemsToBacklogAfterSprintDeletionCommand command) {
        int startBacklogPosition = findNextBacklogPosition.findNextPosition(command.projectId());

        moveWorkItemsFromSprintToBacklog.moveAll(
                command.projectId(),
                command.sprintId(),
                startBacklogPosition
        );
    }
}
