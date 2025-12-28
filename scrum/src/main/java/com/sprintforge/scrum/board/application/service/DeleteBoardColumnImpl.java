package com.sprintforge.scrum.board.application.service;

import com.sprintforge.scrum.board.application.exception.BoardColumnNotFoundException;
import com.sprintforge.scrum.board.application.exception.InvalidTargetBoardColumnException;
import com.sprintforge.scrum.board.application.exception.LastActiveBoardColumnDeletionException;
import com.sprintforge.scrum.board.application.port.in.command.DeleteBoardColumn;
import com.sprintforge.scrum.board.application.port.in.command.DeleteBoardColumnCommand;
import com.sprintforge.scrum.board.application.port.out.persistence.FindBoardColumnById;
import com.sprintforge.scrum.board.application.port.out.persistence.FindLastActiveBoardColumnBySprintIdExcludingId;
import com.sprintforge.scrum.board.application.port.out.persistence.SaveBoardColumn;
import com.sprintforge.scrum.board.application.port.out.persistence.ShiftBoardColumnPositionsAfterDeletion;
import com.sprintforge.scrum.board.domain.BoardColumn;
import com.sprintforge.scrum.workitem.application.port.in.command.ReassignWorkItemsAfterBoardColumnDeletion;
import com.sprintforge.scrum.workitem.application.port.in.command.ReassignWorkItemsAfterBoardColumnDeletionCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class DeleteBoardColumnImpl implements DeleteBoardColumn {

    private final FindBoardColumnById findBoardColumnById;
    private final FindLastActiveBoardColumnBySprintIdExcludingId findLastActiveBoardColumnBySprintIdExcludingId;
    private final SaveBoardColumn saveBoardColumn;
    private final ShiftBoardColumnPositionsAfterDeletion shiftBoardColumnPositionsAfterDeletion;

    private final ReassignWorkItemsAfterBoardColumnDeletion reassignWorkItemsAfterBoardColumnDeletion;

    @Override
    public void handle(DeleteBoardColumnCommand command) {
        BoardColumn boardColumn = findBoardColumnById.findById(command.id())
                .orElseThrow(() -> BoardColumnNotFoundException.byId(command.id()));

        UUID sprintId = boardColumn.getSprint().getId().value();
        UUID deletedColumnId = boardColumn.getId().value();

        BoardColumn fallbackActive = findLastActiveBoardColumnBySprintIdExcludingId
                .findLastActiveBySprintIdExcludingId(sprintId, deletedColumnId)
                .orElseThrow(LastActiveBoardColumnDeletionException::create);

        BoardColumn targetColumn = resolveTargetColumn(command, boardColumn, fallbackActive);

        boolean wasFinal = boardColumn.isFinal();

        UUID projectId = boardColumn.getSprint().getProject().getId().value();

        reassignWorkItemsAfterBoardColumnDeletion.handle(
                new ReassignWorkItemsAfterBoardColumnDeletionCommand(
                        command.employeeId(),
                        projectId,
                        sprintId,
                        deletedColumnId,
                        targetColumn.getId().value()
                )
        );

        boardColumn.delete();
        saveBoardColumn.save(boardColumn);

        shiftBoardColumnPositionsAfterDeletion.shiftLeft(
                sprintId,
                boardColumn.getPosition().value()
        );

        if (wasFinal) {
            targetColumn.markAsFinal();
            saveBoardColumn.save(targetColumn);
        }
    }

    private BoardColumn resolveTargetColumn(DeleteBoardColumnCommand command, BoardColumn deleted, BoardColumn fallbackActive) {
        UUID targetId = command.targetBoardColumnId();
        if (targetId == null) {
            return fallbackActive;
        }

        BoardColumn targetColumn = findBoardColumnById.findById(targetId)
                .orElseThrow(() -> BoardColumnNotFoundException.byId(targetId));

        validateTarget(deleted, targetColumn);

        return targetColumn;
    }

    private void validateTarget(BoardColumn deleted, BoardColumn target) {
        UUID deletedId = deleted.getId().value();
        UUID targetId = target.getId().value();

        if (deletedId.equals(targetId)) {
            throw InvalidTargetBoardColumnException.sameAsDeleted(deletedId);
        }

        UUID deletedSprintId = deleted.getSprint().getId().value();
        UUID targetSprintId = target.getSprint().getId().value();

        if (!deletedSprintId.equals(targetSprintId)) {
            throw InvalidTargetBoardColumnException.differentSprint(targetSprintId);
        }

        if (target.isDeleted()) {
            throw InvalidTargetBoardColumnException.deleted(targetId);
        }
    }
}
