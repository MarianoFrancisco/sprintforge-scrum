package com.sprintforge.scrum.board.application.port.out.persistence;

import com.sprintforge.scrum.board.domain.BoardColumn;

import java.util.Optional;
import java.util.UUID;

public interface FindDefaultBoardColumnBySprintId {
    Optional<BoardColumn> findDefaultBySprintId(UUID sprintId);
}
