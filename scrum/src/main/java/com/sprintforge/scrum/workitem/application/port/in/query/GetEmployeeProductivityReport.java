package com.sprintforge.scrum.workitem.application.port.in.query;

import com.sprintforge.common.application.port.result.EmployeeProductivityReportResult;

public interface GetEmployeeProductivityReport {
    EmployeeProductivityReportResult handle(GetEmployeeProductivityReportQuery query);
}
