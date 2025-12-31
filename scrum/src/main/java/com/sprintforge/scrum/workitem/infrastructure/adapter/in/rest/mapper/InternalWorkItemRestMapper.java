package com.sprintforge.scrum.workitem.infrastructure.adapter.in.rest.mapper;

import com.sprintforge.common.application.port.result.EmployeeProductivityItem;
import com.sprintforge.common.application.port.result.EmployeeProductivityReportResult;
import com.sprintforge.common.application.port.result.EmployeeResult;
import com.sprintforge.common.infrastructure.adapter.in.rest.dto.EmployeeDTO;
import com.sprintforge.common.infrastructure.adapter.in.rest.dto.EmployeeProductivityItemDTO;
import com.sprintforge.common.infrastructure.adapter.in.rest.dto.EmployeeProductivityReportResponseDTO;
import com.sprintforge.scrum.workitem.application.port.in.query.GetEmployeeProductivityReportQuery;
import com.sprintforge.scrum.workitem.infrastructure.adapter.in.rest.dto.EmployeeProductivityReportRequestDTO;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class InternalWorkItemRestMapper {

    public GetEmployeeProductivityReportQuery toQuery(
            EmployeeProductivityReportRequestDTO dto
    ) {
        return new GetEmployeeProductivityReportQuery(
                dto.from(),
                dto.to(),
                dto.employeeId()
        );
    }

    public EmployeeProductivityReportResponseDTO fromResult(
            EmployeeProductivityReportResult result
    ) {
        return new EmployeeProductivityReportResponseDTO(
                result.from(),
                result.to(),
                mapEmployees(result.employees()),
                result.totalEmployees()
        );
    }

    private List<EmployeeProductivityItemDTO> mapEmployees(
            List<EmployeeProductivityItem> employees
    ) {
        return employees.stream()
                .map(InternalWorkItemRestMapper::mapEmployee)
                .toList();
    }

    private EmployeeProductivityItemDTO mapEmployee(
            EmployeeProductivityItem e
    ) {
        return new EmployeeProductivityItemDTO(
                mapEmployeeDto(e.employee()),
                e.workedStories(),
                e.completedStories(),
                e.pendingStories(),
                e.deliveredStoryPoints(),
                e.hoursWorked()
        );
    }

    private EmployeeDTO mapEmployeeDto(EmployeeResult e) {
        return new EmployeeDTO(
                e.id(),
                e.email(),
                e.fullName(),
                e.profileImage(),
                e.position()
        );
    }
}
