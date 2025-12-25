package com.sprintforge.scrum.project.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public record ProjectBudgetAmount(
        BigDecimal value
) {
    public ProjectBudgetAmount {
        if (value != null && value.compareTo(ZERO) < 0) {
            throw new ValidationException("El monto del presupuesto del proyecto no puede ser negativo");
        }
    }

    public static ProjectBudgetAmount of(BigDecimal value) {
        return new ProjectBudgetAmount(value);
    }
}
