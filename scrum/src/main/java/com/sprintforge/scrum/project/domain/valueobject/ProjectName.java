package com.sprintforge.scrum.project.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public record ProjectName(String value) {

    private static final int MAX_LENGTH = 50;

    public ProjectName {
        if (value == null || value.isBlank()) {
            throw new ValidationException("El nombre del proyecto no puede estar vacío");
        }
        if (value.length() > MAX_LENGTH) {
            throw new ValidationException(
                    "El nombre del proyecto no puede exceder los " + MAX_LENGTH + " caracteres"
            );
        }
    }

    public static ProjectName of(String value) {
        return new ProjectName(value);
    }
}