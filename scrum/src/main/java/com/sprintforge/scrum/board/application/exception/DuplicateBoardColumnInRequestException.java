package com.sprintforge.scrum.board.application.exception;

import com.sprintforge.common.application.exception.BusinessException;

public class DuplicateBoardColumnInRequestException extends BusinessException {

    private DuplicateBoardColumnInRequestException() {
        super("Hay columnas repetidas en la solicitud.");
    }

    public static DuplicateBoardColumnInRequestException create() {
        return new DuplicateBoardColumnInRequestException();
    }
}
