package com.sprintforge.scrum.project.infrastructure.adapter.in.rest.mapper;

import com.sprintforge.scrum.project.domain.valueobject.ProjectEmployeeAssignment;
import com.sprintforge.scrum.project.infrastructure.adapter.in.rest.dto.ProjectEmployeeResponseDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProjectEmployeeRestMapper {

    public ProjectEmployeeResponseDTO toResponse(ProjectEmployeeAssignment assignment) {
        if (assignment == null) {
            return null;
        }

        return new ProjectEmployeeResponseDTO(
                assignment.getEmployeeId().value(),
                assignment.getAssignedAt()
        );
    }
}
