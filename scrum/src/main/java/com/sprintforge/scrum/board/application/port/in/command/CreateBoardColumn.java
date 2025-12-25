package com.sprintforge.scrum.board.application.port.in.command;

import com.sprintforge.scrum.board.domain.BoardColumn;

public interface CreateBoardColumn {
    BoardColumn handle(CreateBoardColumnCommand command);
}
