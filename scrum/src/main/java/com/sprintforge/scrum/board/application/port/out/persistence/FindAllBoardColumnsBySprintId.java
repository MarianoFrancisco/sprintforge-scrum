package com.sprintforge.scrum.board.application.port.out.persistence;

import com.sprintforge.scrum.board.application.port.in.query.GetAllBoardColumnsBySprintIdQuery;
import com.sprintforge.scrum.board.domain.BoardColumn;

import java.util.List;

public interface FindAllBoardColumnsBySprintId {
    List<BoardColumn> findAllBySprintId(GetAllBoardColumnsBySprintIdQuery query);
}
