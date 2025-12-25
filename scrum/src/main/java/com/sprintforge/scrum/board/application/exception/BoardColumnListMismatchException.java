package com.sprintforge.scrum.board.application.exception;

import com.sprintforge.common.application.exception.BusinessException;

public class BoardColumnListMismatchException extends BusinessException {

    private BoardColumnListMismatchException(String message) {
        super(message);
    }

    public static BoardColumnListMismatchException activeColumnsMismatch() {
        return new BoardColumnListMismatchException(
                "Las columnas enviadas no coinciden con las columnas activas del sprint."
        );
    }
}
