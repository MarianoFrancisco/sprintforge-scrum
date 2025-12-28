package com.sprintforge.scrum.sprint.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public enum SprintStatus {
    CREATED,
    STARTED,
    COMPLETED;

    public static SprintStatus safeValueOf(String value) {
        if (value == null) {
            throw new ValidationException("El estado del sprint no puede estar vacío");
        }
        try {
            return SprintStatus.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("El estado del sprint es inválido: " + value);
        }
    }
}
