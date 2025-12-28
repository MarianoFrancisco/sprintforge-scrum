package com.sprintforge.scrum.board.application.service;

import com.sprintforge.scrum.board.application.exception.BoardColumnNotFoundException;
import com.sprintforge.scrum.board.application.exception.DuplicateBoardColumnException;
import com.sprintforge.scrum.board.application.port.in.command.UpdateBoardColumnName;
import com.sprintforge.scrum.board.application.port.in.command.UpdateBoardColumnNameCommand;
import com.sprintforge.scrum.board.application.port.out.persistence.ExistsBoardColumnByNameAndIdNot;
import com.sprintforge.scrum.board.application.port.out.persistence.FindBoardColumnById;
import com.sprintforge.scrum.board.application.port.out.persistence.SaveBoardColumn;
import com.sprintforge.scrum.board.domain.BoardColumn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UpdateBoardColumnNameImpl implements UpdateBoardColumnName {

    private final FindBoardColumnById findBoardColumnById;
    private final ExistsBoardColumnByNameAndIdNot existsBoardColumnByNameAndIdNot;
    private final SaveBoardColumn saveBoardColumn;

    @Override
    public BoardColumn handle(UpdateBoardColumnNameCommand command) {
        BoardColumn boardColumn = findBoardColumnById.findById(command.id()).orElseThrow(
                () -> BoardColumnNotFoundException.byId(command.id())
        );
        if (existsBoardColumnByNameAndIdNot.existsByNameAndIdNot(command.name(), command.id())) {
            throw DuplicateBoardColumnException.byName(command.name());
        }
        boardColumn.updateName(command.name());
        BoardColumn savedBoardColumn = saveBoardColumn.save(boardColumn);
        return savedBoardColumn;
    }
}
