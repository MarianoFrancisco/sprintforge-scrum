package com.sprintforge.scrum.board.application.exception;

import com.sprintforge.common.application.exception.BusinessException;

import java.util.UUID;

public class BoardColumnNotFoundException extends BusinessException {

    private BoardColumnNotFoundException(String message) {
        super(message);
    }

    private BoardColumnNotFoundException(String field, String value) {
        super(String.format(
                "No se encontró ninguna columna de tablero con %s \"%s\".",
                field,
                value
        ));
    }

    public static BoardColumnNotFoundException byId(UUID id) {
        return new BoardColumnNotFoundException("el identificador", id.toString());
    }

    public static BoardColumnNotFoundException byName(String name) {
        return new BoardColumnNotFoundException("el nombre", name);
    }

    public static BoardColumnNotFoundException finalColumnNotFound(UUID sprintId) {
        return new BoardColumnNotFoundException(
                String.format(
                        "No existe ninguna columna marcada como final para el sprint \"%s\".",
                        sprintId
                )
        );
    }

    public static BoardColumnNotFoundException defaultColumnNotFound(UUID sprintId) {
        return new BoardColumnNotFoundException(
                String.format(
                        "No existe ninguna columna predeterminada para el sprint \"%s\".",
                        sprintId
                )
        );
    }
}
