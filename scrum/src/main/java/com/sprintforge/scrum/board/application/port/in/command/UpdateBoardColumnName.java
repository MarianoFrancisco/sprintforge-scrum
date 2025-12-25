package com.sprintforge.scrum.board.application.port.in.command;

import com.sprintforge.scrum.board.domain.BoardColumn;

public interface UpdateBoardColumnName {
    BoardColumn handle(UpdateBoardColumnNameCommand command);
}
