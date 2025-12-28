package com.sprintforge.scrum.board.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

import java.util.UUID;

public record BoardColumnId(UUID value) {
    public BoardColumnId {
        if (value == null) {
            throw new ValidationException("El identificador de la columna del tablero no puede estar vacío");
        }
    }

    public static BoardColumnId of(UUID value) {
        return new BoardColumnId(value);
    }
}
