package com.sprintforge.scrum.workitem.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public record WorkItemTitle(String value) {

    private static final int MAX_LENGTH = 200;

    public WorkItemTitle {
        if (value == null) {
            throw new ValidationException("El título de la Historia de Usuario no puede ser nulo");
        }

        String normalized = value.trim().replaceAll("\\s+", " ");

        if (normalized.isBlank()) {
            throw new ValidationException("El título de la Historia de Usuario no puede estar vacío");
        }

        if (normalized.length() > MAX_LENGTH) {
            throw new ValidationException("El título de la Historia de Usuario no puede exceder los " + MAX_LENGTH + " caracteres");
        }

        value = normalized;
    }

    public static WorkItemTitle of(String value) {
        return new WorkItemTitle(value);
    }
}
