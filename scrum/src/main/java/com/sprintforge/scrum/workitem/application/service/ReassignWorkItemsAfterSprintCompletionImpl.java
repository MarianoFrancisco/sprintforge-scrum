package com.sprintforge.scrum.workitem.application.service;

import com.sprintforge.scrum.board.application.port.in.query.GetDefaultBoardColumnBySprint;
import com.sprintforge.scrum.board.application.port.in.query.GetDefaultBoardColumnBySprintQuery;
import com.sprintforge.scrum.board.domain.BoardColumn;
import com.sprintforge.scrum.workitem.application.port.in.command.ReassignWorkItemsAfterSprintCompletion;
import com.sprintforge.scrum.workitem.application.port.in.command.ReassignWorkItemsAfterSprintCompletionCommand;
import com.sprintforge.scrum.workitem.application.port.out.persistence.FindNextBacklogPosition;
import com.sprintforge.scrum.workitem.application.port.out.persistence.FindNextBoardColumnPosition;
import com.sprintforge.scrum.workitem.application.port.out.persistence.MoveUnfinishedWorkItemsFromSprintToBacklog;
import com.sprintforge.scrum.workitem.application.port.out.persistence.MoveUnfinishedWorkItemsFromSprintToSprint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ReassignWorkItemsAfterSprintCompletionImpl implements ReassignWorkItemsAfterSprintCompletion {

    private final GetDefaultBoardColumnBySprint getDefaultBoardColumnBySprint;
    private final FindNextBoardColumnPosition findNextBoardColumnPosition;
    private final FindNextBacklogPosition findNextBacklogPosition;

    private final MoveUnfinishedWorkItemsFromSprintToSprint moveUnfinishedWorkItemsFromSprintToSprint;
    private final MoveUnfinishedWorkItemsFromSprintToBacklog moveUnfinishedWorkItemsFromSprintToBacklog;

    @Override
    public void handle(ReassignWorkItemsAfterSprintCompletionCommand command) {
        if (command.targetSprintId() == null) {
            moveToBacklog(command.projectId(), command.completedSprintId());
            return;
        }
        moveToSprint(command.projectId(), command.completedSprintId(), command.targetSprintId());
    }

    private void moveToSprint(UUID projectId, UUID completedSprintId, UUID targetSprintId) {
        BoardColumn defaultColumn = getDefaultBoardColumnBySprint.handle(
                new GetDefaultBoardColumnBySprintQuery(targetSprintId)
        );

        int startPosition = findNextBoardColumnPosition.findNextPosition(
                projectId,
                targetSprintId,
                defaultColumn.getId().value()
        );

        moveUnfinishedWorkItemsFromSprintToSprint.moveAll(
                projectId,
                completedSprintId,
                targetSprintId,
                defaultColumn.getId().value(),
                startPosition
        );
    }

    private void moveToBacklog(UUID projectId, UUID completedSprintId) {
        int startPosition = findNextBacklogPosition.findNextPosition(projectId);

        moveUnfinishedWorkItemsFromSprintToBacklog.moveAll(
                projectId,
                completedSprintId,
                startPosition
        );
    }
}
