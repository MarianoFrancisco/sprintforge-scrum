package com.sprintforge.scrum.common.infrastructure.adapter.out.rest.employee.mapper;

import com.sprintforge.common.application.port.result.EmployeeResult;
import com.sprintforge.scrum.common.infrastructure.adapter.in.rest.dto.EmployeeResponseDTO;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class EmployeeResultMapper {

    public List<EmployeeResult> toResult(List<EmployeeResponseDTO> response) {
        return response.stream()
                .map(EmployeeResultMapper::toResult)
                .toList();
    }

    public EmployeeResult toResult(EmployeeResponseDTO response) {
        return new EmployeeResult(
                response.id(),
                response.email(),
                response.fullName(),
                response.profileImage(),
                response.position().name()
        );
    }
}
