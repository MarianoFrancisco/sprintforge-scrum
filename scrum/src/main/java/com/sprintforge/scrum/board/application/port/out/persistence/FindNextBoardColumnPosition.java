package com.sprintforge.scrum.board.application.port.out.persistence;

import java.util.UUID;

public interface FindNextBoardColumnPosition {
    int findNextPosition(UUID sprintId);
}
