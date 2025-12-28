package com.sprintforge.scrum.project.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public record ProjectArea(
        String value
) {
    public ProjectArea {
        if (value == null) {
            throw new ValidationException("El área del proyecto no puede ser nulo");
        }

        String normalized = value.trim().replaceAll("\\s+", " ");

        if (normalized.isBlank()) {
            throw new ValidationException("El área del proyecto no puede estar vacío");
        }

        if (normalized.length() > 80) {
            throw new ValidationException("El área del proyecto no puede tener más de 80 caracteres");
        }

        value = normalized;
    }

    public static ProjectArea of(String value) {
        return new ProjectArea(value);
    }
}
