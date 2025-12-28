package com.sprintforge.scrum.board.application.service;

import com.sprintforge.scrum.board.application.exception.*;
import com.sprintforge.scrum.board.application.port.in.command.MoveBoardColumn;
import com.sprintforge.scrum.board.application.port.in.command.MoveBoardColumnCommand;
import com.sprintforge.scrum.board.application.port.out.persistence.*;
import com.sprintforge.scrum.board.domain.BoardColumn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MoveBoardColumnImpl implements MoveBoardColumn {

    private final FindBoardColumnById findBoardColumnById;
    private final ShiftBoardColumnPositions shiftBoardColumnPositions;
    private final FindLastActiveBoardColumnBySprintId findLastActiveBoardColumnBySprintId;
    private final FindBoardColumnBySprintIdAndIsFinalTrue findBoardColumnBySprintIdAndIsFinalTrue;
    private final SaveBoardColumn saveBoardColumn;

    @Override
    public BoardColumn handle(MoveBoardColumnCommand command) {
        BoardColumn boardColumn = findBoardColumnById.findById(command.id())
                .orElseThrow(() -> BoardColumnNotFoundException.byId(command.id()));

        UUID sprintId = boardColumn.getSprint().getId().value();

        int oldPosition = boardColumn.getPosition().value();
        int newPosition = command.newPosition();

        if (newPosition < 0) {
            throw InvalidBoardColumnPositionException.negative();
        }
        if (newPosition == oldPosition) return boardColumn;

        BoardColumn last = findLastActiveBoardColumnBySprintId.findLastActiveBySprintId(sprintId)
                .orElseThrow(BoardColumnPositionOutOfRangeException::create);

        int lastPosition = last.getPosition().value();

        if (newPosition > lastPosition) throw BoardColumnPositionOutOfRangeException.create();

        BoardColumn oldFinal = findBoardColumnBySprintIdAndIsFinalTrue
                .findBySprintIdAndIsFinalTrue(sprintId)
                .orElseThrow(() -> BoardColumnNotFoundException.finalColumnNotFound(sprintId));

        if (newPosition > oldPosition) {
            shiftBoardColumnPositions.shiftDown(sprintId, oldPosition, newPosition);
        } else {
            shiftBoardColumnPositions.shiftUp(sprintId, newPosition, oldPosition);
        }

        boardColumn.updatePosition(newPosition);
        saveBoardColumn.save(boardColumn);

        BoardColumn newLast = findLastActiveBoardColumnBySprintId.findLastActiveBySprintId(sprintId)
                .orElseThrow(BoardColumnPositionOutOfRangeException::create);

        UUID newLastId = newLast.getId().value();
        UUID oldFinalId = oldFinal.getId().value();

        if (!newLast.isFinal()) {
            newLast.markAsFinal();
            saveBoardColumn.save(newLast);
        }

        if (!oldFinalId.equals(newLastId) && oldFinal.isFinal()) {
            oldFinal.unmarkAsFinal();
            saveBoardColumn.save(oldFinal);
        }

        return boardColumn;
    }
}

