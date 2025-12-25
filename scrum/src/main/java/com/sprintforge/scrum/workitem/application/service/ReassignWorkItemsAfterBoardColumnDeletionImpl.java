package com.sprintforge.scrum.workitem.application.service;

import com.sprintforge.scrum.workitem.application.port.in.command.ReassignWorkItemsAfterBoardColumnDeletion;
import com.sprintforge.scrum.workitem.application.port.in.command.ReassignWorkItemsAfterBoardColumnDeletionCommand;
import com.sprintforge.scrum.workitem.application.port.out.persistence.FindNextBoardColumnPosition;
import com.sprintforge.scrum.workitem.application.port.out.persistence.MoveWorkItemsFromBoardColumnToAnother;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ReassignWorkItemsAfterBoardColumnDeletionImpl
        implements ReassignWorkItemsAfterBoardColumnDeletion {

    private final FindNextBoardColumnPosition findNextBoardColumnPosition;
    private final MoveWorkItemsFromBoardColumnToAnother moveWorkItemsFromBoardColumnToAnother;

    @Override
    public void handle(ReassignWorkItemsAfterBoardColumnDeletionCommand command) {
        int startPositionInTarget = findNextBoardColumnPosition.findNextPosition(
                command.projectId(),
                command.sprintId(),
                command.fallbackBoardColumnId()
        );

        moveWorkItemsFromBoardColumnToAnother.moveAll(
                command.projectId(),
                command.sprintId(),
                command.deletedBoardColumnId(),
                command.fallbackBoardColumnId(),
                startPositionInTarget
        );
    }
}
