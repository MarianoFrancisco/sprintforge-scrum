package com.sprintforge.scrum.workitem.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record UpdateWorkItemStoryPointsRequestDTO(
        @NotNull(message = "El empleado que realiza la acción es obligatorio")
        UUID employeeId,

        @Min(value = 0, message = "Los story points no pueden ser negativos")
        Integer storyPoints
) {
}
