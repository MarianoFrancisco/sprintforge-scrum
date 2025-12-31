package com.sprintforge.scrum.common.application.service.support;

import com.sprintforge.scrum.common.application.port.out.rest.employee.query.GetEmployeesByIds;
import com.sprintforge.scrum.common.application.port.out.rest.employee.query.GetEmployeesByIdsQuery;
import com.sprintforge.common.application.port.result.EmployeeResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EmployeeQuerySupport {

    private final GetEmployeesByIds getEmployeesByIds;

    public EmployeeResult getEmployee(UUID employeeId) {
        return getEmployeesByIds
                .getByIds(new GetEmployeesByIdsQuery(Set.of(employeeId)))
                .getFirst();
    }
}
