package com.sprintforge.scrum.board.application.port.out.persistence;

import com.sprintforge.scrum.board.domain.BoardColumn;

import java.util.List;

public interface SaveAllBoardColumn {
    List<BoardColumn> saveAll(List<BoardColumn> boardColumns);
}
