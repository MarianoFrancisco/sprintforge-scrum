package com.sprintforge.scrum.common.application.port.out.rest.employee.query;

import com.sprintforge.common.application.port.result.EmployeeResult;

import java.util.List;

public interface GetEmployeesByIds {
    List<EmployeeResult> getByIds(GetEmployeesByIdsQuery query);
}
