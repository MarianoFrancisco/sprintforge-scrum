package com.sprintforge.scrum.board.application.exception;

import com.sprintforge.common.application.exception.BusinessException;

public class InvalidBoardColumnPositionException extends BusinessException {

    private InvalidBoardColumnPositionException() {
        super("La posición de una columna no puede ser negativa.");
    }

    public static InvalidBoardColumnPositionException negative() {
        return new InvalidBoardColumnPositionException();
    }
}
