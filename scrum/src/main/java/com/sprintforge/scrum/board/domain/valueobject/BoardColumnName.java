package com.sprintforge.scrum.board.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public record BoardColumnName(String value) {

    private static final int MAX_LENGTH = 60;

    public BoardColumnName {
        if (value == null) {
            throw new ValidationException("El nombre de la columna no puede ser nulo");
        }

        String normalized = value.trim().replaceAll("\\s+", " ");

        if (normalized.isBlank()) {
            throw new ValidationException("El nombre de la columna no puede estar vacío");
        }

        if (normalized.length() > MAX_LENGTH) {
            throw new ValidationException("El nombre de la columna no puede exceder los " + MAX_LENGTH + " caracteres");
        }

        value = normalized;
    }

    public static BoardColumnName of(String value) {
        return new BoardColumnName(value);
    }
}
