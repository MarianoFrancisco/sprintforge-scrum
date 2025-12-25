package com.sprintforge.scrum.workitem.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public record WorkItemAcceptanceCriteria(String value) {

    private static final int MAX_LENGTH = 2000;

    public WorkItemAcceptanceCriteria {
        if (value == null) {
            throw new ValidationException("Los criterios de aceptación no pueden ser nulos");
        }

        String normalized = value.trim();

        if (normalized.isBlank()) {
            throw new ValidationException("Los criterios de aceptación no pueden estar vacíos");
        }

        if (normalized.length() > MAX_LENGTH) {
            throw new ValidationException("Los criterios de aceptación no pueden exceder los " + MAX_LENGTH + " caracteres");
        }

        value = normalized;
    }

    public static WorkItemAcceptanceCriteria of(String value) {
        return new WorkItemAcceptanceCriteria(value);
    }
}
