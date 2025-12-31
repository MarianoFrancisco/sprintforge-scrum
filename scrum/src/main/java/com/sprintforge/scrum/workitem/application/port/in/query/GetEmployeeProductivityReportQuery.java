package com.sprintforge.scrum.workitem.application.port.in.query;

import java.time.LocalDate;
import java.util.UUID;

public record GetEmployeeProductivityReportQuery(
        LocalDate from,
        LocalDate to,
        UUID employeeId
) {
}
