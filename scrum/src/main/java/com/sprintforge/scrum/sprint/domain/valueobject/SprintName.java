package com.sprintforge.scrum.sprint.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public record SprintName(String value) {

    private static final int MAX_LENGTH = 120;

    public SprintName {
        if (value == null) {
            throw new ValidationException("El nombre del sprint no puede ser nulo");
        }

        String normalized = value.trim().replaceAll("\\s+", " ");

        if (normalized.isBlank()) {
            throw new ValidationException("El nombre del sprint no puede estar vacío");
        }

        if (normalized.length() > MAX_LENGTH) {
            throw new ValidationException("El nombre del sprint no puede exceder los " + MAX_LENGTH + " caracteres");
        }

        value = normalized;
    }

    public static SprintName of(String value) {
        return new SprintName(value);
    }
}
