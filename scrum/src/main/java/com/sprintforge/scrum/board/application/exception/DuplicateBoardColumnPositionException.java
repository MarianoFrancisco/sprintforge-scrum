package com.sprintforge.scrum.board.application.exception;

import com.sprintforge.common.application.exception.BusinessException;

public class DuplicateBoardColumnPositionException extends BusinessException {

    private DuplicateBoardColumnPositionException() {
        super("Hay más de una columna con la misma posición.");
    }

    public static DuplicateBoardColumnPositionException create() {
        return new DuplicateBoardColumnPositionException();
    }
}
