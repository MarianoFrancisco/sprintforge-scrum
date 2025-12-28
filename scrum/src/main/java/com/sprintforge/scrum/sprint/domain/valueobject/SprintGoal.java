package com.sprintforge.scrum.sprint.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public record SprintGoal(String value) {

    private static final int MAX_LENGTH = 500;

    public SprintGoal {
        if (value != null) {
            String normalized = value.trim().replaceAll("\\s+", " ");

            if (normalized.isBlank()) {
                throw new ValidationException("La meta del sprint no puede estar vacía");
            }

            if (normalized.length() > MAX_LENGTH) {
                throw new ValidationException("La meta del sprint no puede tener más de " + MAX_LENGTH + " caracteres");
            }

            value = normalized;
        }
    }

    public static SprintGoal of(String value) {
        return new SprintGoal(value);
    }
}
