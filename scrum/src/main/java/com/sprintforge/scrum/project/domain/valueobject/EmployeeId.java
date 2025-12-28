package com.sprintforge.scrum.project.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

import java.util.UUID;

public record EmployeeId(UUID value) {
    public EmployeeId {
        if (value == null) {
            throw new ValidationException("El identificador del empleado no puede estar vacío");
        }
    }

    public static EmployeeId of(UUID value) {
        return new EmployeeId(value);
    }
}
