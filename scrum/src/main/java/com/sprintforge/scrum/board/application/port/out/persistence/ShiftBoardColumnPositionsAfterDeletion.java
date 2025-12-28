package com.sprintforge.scrum.board.application.port.out.persistence;

import java.util.UUID;

public interface ShiftBoardColumnPositionsAfterDeletion {
    void shiftLeft(UUID sprintId, int deletedPosition);
}
