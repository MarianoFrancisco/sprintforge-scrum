package com.sprintforge.scrum.sprint.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

import java.time.Instant;

public record SprintEndDate(Instant value) {
    public SprintEndDate {
        if (value == null) {
            throw new ValidationException("La fecha de fin del sprint no puede ser nula");
        }
    }

    public static SprintEndDate of(Instant value) {
        return new SprintEndDate(value);
    }
}
