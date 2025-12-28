package com.sprintforge.scrum.board.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public record BoardColumnPosition(int value) {

    public BoardColumnPosition {
        if (value < 0) {
            throw new ValidationException("La posición de la columna no puede ser menor a 0");
        }
    }

    public static BoardColumnPosition of(int value) {
        return new BoardColumnPosition(value);
    }
}
