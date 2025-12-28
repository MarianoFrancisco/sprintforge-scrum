package com.sprintforge.scrum.board.application.exception;

import com.sprintforge.common.application.exception.BusinessException;

public class BoardColumnPositionOutOfRangeException extends BusinessException {

    private BoardColumnPositionOutOfRangeException() {
        super("La posición de una columna no puede ser mayor o igual al total de columnas.");
    }

    public static BoardColumnPositionOutOfRangeException create() {
        return new BoardColumnPositionOutOfRangeException();
    }
}
