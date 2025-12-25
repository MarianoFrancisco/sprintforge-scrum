package com.sprintforge.scrum.board.application.port.in.query;

import com.sprintforge.scrum.board.domain.BoardColumn;

import java.util.List;

public interface GetAllBoardColumnsBySprintId {
    List<BoardColumn> handle(GetAllBoardColumnsBySprintIdQuery query);
}
