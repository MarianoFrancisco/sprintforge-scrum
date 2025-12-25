package com.sprintforge.scrum.project.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public record ProjectKey(String value) {

    private static final int MAX_LENGTH = 10;

    public ProjectKey {
        if (value == null || value.isBlank()) {
            throw new ValidationException("La clave del proyecto no puede estar vacía");
        }
        if (value.length() > MAX_LENGTH) {
            throw new ValidationException(
                    "La clave del proyecto no puede exceder los " + MAX_LENGTH + " caracteres"
            );
        }
    }

    public static ProjectKey of(String value) {
        return new ProjectKey(value);
    }
}
