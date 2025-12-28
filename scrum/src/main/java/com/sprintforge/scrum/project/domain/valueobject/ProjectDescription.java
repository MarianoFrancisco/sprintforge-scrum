package com.sprintforge.scrum.project.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public record ProjectDescription(String value) {

    private static final int MAX_LENGTH = 255;

    public ProjectDescription {
        if (value != null && value.length() > MAX_LENGTH) {
            throw new ValidationException(
                    "La descripción del proyecto no puede exceder los " + MAX_LENGTH + " caracteres"
            );
        }
    }

    public static ProjectDescription of(String value) {
        return new ProjectDescription(value);
    }
}