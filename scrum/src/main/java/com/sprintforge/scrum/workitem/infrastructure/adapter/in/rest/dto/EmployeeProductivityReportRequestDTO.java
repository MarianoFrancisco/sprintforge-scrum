package com.sprintforge.scrum.workitem.infrastructure.adapter.in.rest.dto;

import java.time.LocalDate;
import java.util.UUID;

public record EmployeeProductivityReportRequestDTO(
        LocalDate from,
        LocalDate to,
        UUID employeeId
) {
}
