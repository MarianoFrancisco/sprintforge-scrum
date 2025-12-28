package com.sprintforge.scrum.board.application.exception;

import com.sprintforge.common.application.exception.BusinessException;

import java.util.UUID;

public class FirstBoardColumnNotFoundException extends BusinessException {

    private FirstBoardColumnNotFoundException(String message) {
        super(message);
    }

    public static FirstBoardColumnNotFoundException forSprintId(UUID sprintId) {
        return new FirstBoardColumnNotFoundException(
                String.format(
                        "No se encontró ninguna columna inicial del tablero para el sprint \"%s\".",
                        sprintId
                )
        );
    }
}
