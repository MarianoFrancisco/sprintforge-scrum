package com.sprintforge.scrum.board.application.port.out.persistence;

import java.util.UUID;

public interface ShiftBoardColumnPositions {
    void shiftUp(UUID sprintId, int fromInclusive, int toExclusive);

    void shiftDown(UUID sprintId, int fromExclusive, int toInclusive);
}
