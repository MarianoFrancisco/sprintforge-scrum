package com.sprintforge.scrum.project.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public record ProjectClient(String value) {

    public ProjectClient {
        if (value == null) {
            throw new ValidationException("El cliente del proyecto no puede ser nulo");
        }

        String normalized = value.trim().replaceAll("\\s+", " ");

        if (normalized.isBlank()) {
            throw new ValidationException("El cliente del proyecto no puede estar vacío");
        }

        if (normalized.length() > 100) {
            throw new ValidationException("El cliente del proyecto no puede tener más de 100 caracteres");
        }

        value = normalized;
    }

    public static ProjectClient of(String value) {
        return new ProjectClient(value);
    }
}
