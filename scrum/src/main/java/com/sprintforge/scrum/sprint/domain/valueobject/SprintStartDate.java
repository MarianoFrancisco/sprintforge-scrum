package com.sprintforge.scrum.sprint.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

import java.time.Instant;

public record SprintStartDate(Instant value) {
    public SprintStartDate {
        if (value == null) {
            throw new ValidationException("La fecha de inicio del sprint no puede ser nula");
        }
    }

    public static SprintStartDate of(Instant value) {
        return new SprintStartDate(value);
    }
}
