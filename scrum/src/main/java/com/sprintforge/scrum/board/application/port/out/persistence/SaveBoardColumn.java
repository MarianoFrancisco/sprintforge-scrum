package com.sprintforge.scrum.board.application.port.out.persistence;

import com.sprintforge.scrum.board.domain.BoardColumn;

public interface SaveBoardColumn {
    BoardColumn save(BoardColumn boardColumn);
}
