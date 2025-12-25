package com.sprintforge.scrum.workitem.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public record WorkItemDescription(String value) {

    private static final int MAX_LENGTH = 2000;

    public WorkItemDescription {
        if (value == null) {
            throw new ValidationException("La descripción de la Historia de Usuario no puede ser nula");
        }

        String normalized = value.trim();

        if (normalized.isBlank()) {
            throw new ValidationException("La descripción de la Historia de Usuario no puede estar vacía");
        }

        if (normalized.length() > MAX_LENGTH) {
            throw new ValidationException("La descripción de la Historia de Usuario no puede exceder los " + MAX_LENGTH + " caracteres");
        }

        value = normalized;
    }

    public static WorkItemDescription of(String value) {
        return new WorkItemDescription(value);
    }
}
