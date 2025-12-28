package com.sprintforge.scrum.project.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public record ProjectContractAmount(
        BigDecimal value
) {
    public ProjectContractAmount {
        if (value != null && value.compareTo(ZERO) < 0) {
            throw new ValidationException("El monto del contrato del proyecto no puede ser negativo");
        }
    }

    public static ProjectContractAmount of(BigDecimal value) {
        return new ProjectContractAmount(value);
    }
}
