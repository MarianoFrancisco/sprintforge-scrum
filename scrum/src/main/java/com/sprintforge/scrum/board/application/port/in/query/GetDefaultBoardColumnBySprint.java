package com.sprintforge.scrum.board.application.port.in.query;

import com.sprintforge.scrum.board.domain.BoardColumn;

public interface GetDefaultBoardColumnBySprint {
    BoardColumn handle(GetDefaultBoardColumnBySprintQuery query);
}
