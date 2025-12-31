package com.sprintforge.scrum.workitem.application.port.out.persistence;

import com.sprintforge.scrum.workitem.application.port.in.query.GetEmployeeProductivityReportQuery;
import com.sprintforge.scrum.workitem.application.port.out.persistence.raw.EmployeeProductivityReportRaw;

public interface LoadEmployeeProductivityReportRaw {
    EmployeeProductivityReportRaw load(GetEmployeeProductivityReportQuery query);
}
