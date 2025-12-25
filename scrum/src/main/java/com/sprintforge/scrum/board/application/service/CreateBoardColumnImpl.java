package com.sprintforge.scrum.board.application.service;

import com.sprintforge.scrum.board.application.exception.BoardColumnNotFoundException;
import com.sprintforge.scrum.board.application.exception.DuplicateBoardColumnException;
import com.sprintforge.scrum.board.application.mapper.BoardColumnMapper;
import com.sprintforge.scrum.board.application.port.in.command.CreateBoardColumn;
import com.sprintforge.scrum.board.application.port.in.command.CreateBoardColumnCommand;
import com.sprintforge.scrum.board.application.port.out.persistence.*;
import com.sprintforge.scrum.board.domain.BoardColumn;
import com.sprintforge.scrum.sprint.application.port.in.query.GetSprintById;
import com.sprintforge.scrum.sprint.application.port.in.query.GetSprintByIdQuery;
import com.sprintforge.scrum.sprint.domain.Sprint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CreateBoardColumnImpl implements CreateBoardColumn {

    private final ExistsBoardColumnBySprintIdAndName existsBoardColumnBySprintAndName;
    private final GetSprintById getSprintById;
    private final FindNextBoardColumnPosition findNextBoardColumnPosition;
    private final FindBoardColumnBySprintIdAndIsFinalTrue findBoardColumnBySprintIdAndIsFinalTrue;
    private final SaveBoardColumn saveBoardColumn;

    @Override
    public BoardColumn handle(CreateBoardColumnCommand command) {
        Sprint sprint = getSprintById.handle(new GetSprintByIdQuery(command.sprintId()));
        if (existsBoardColumnBySprintAndName.existsBySprintIdAndName(command.sprintId(), command.name())) {
            throw DuplicateBoardColumnException.bySprintAndName(command.sprintId(), command.name());
        }

        int position = findNextBoardColumnPosition.findNextPosition(command.sprintId());

        BoardColumn finalBoardColumn = findBoardColumnBySprintIdAndIsFinalTrue.findBySprintIdAndIsFinalTrue(command.sprintId()).orElseThrow(
                () -> BoardColumnNotFoundException.finalColumnNotFound(command.sprintId())
        );
        BoardColumn boardColumn = BoardColumnMapper.toDomain(command, position, sprint);

        finalBoardColumn.unmarkAsFinal();

        saveBoardColumn.save(finalBoardColumn);
        BoardColumn boardColumnSaved = saveBoardColumn.save(boardColumn);
        return boardColumnSaved;
    }
}
