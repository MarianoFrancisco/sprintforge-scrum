package com.sprintforge.scrum.workitem.application.port.out.persistence.raw;

import java.time.LocalDate;
import java.util.List;

public record EmployeeProductivityReportRaw(
        LocalDate from,
        LocalDate to,
        List<EmployeeProductivityRawItem> employees,
        long totalEmployees
) {
}
