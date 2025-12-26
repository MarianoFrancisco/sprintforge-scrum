package com.sprintforge.scrum.common.infrastructure.adapter.in.rest.mapper;

import com.sprintforge.scrum.common.application.port.result.EmployeeResult;
import com.sprintforge.scrum.common.infrastructure.adapter.in.rest.dto.EmployeeResultResponseDTO;
import com.sprintforge.scrum.project.application.port.in.command.*;
import com.sprintforge.scrum.project.infrastructure.adapter.in.rest.dto.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EmployeeResultRestMapper {

    public EmployeeResultResponseDTO toResponse(EmployeeResult employee) {
        if (employee == null) {
            return null;
        }

        return new EmployeeResultResponseDTO(
                employee.id(),
                employee.email(),
                employee.fullName(),
                employee.profileImage(),
                employee.position()
        );
    }
}
