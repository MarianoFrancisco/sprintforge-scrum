package com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.mapper;

import com.sprintforge.scrum.project.domain.valueobject.ProjectEmployeeAssignment;
import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.entity.ProjectEmployeeEntity;
import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.entity.ProjectEmployeeId;
import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.entity.ProjectEntity;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@UtilityClass
public class ProjectEmployeeEntityMapper {

    public Set<ProjectEmployeeAssignment> toDomain(Set<ProjectEmployeeEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return Set.of();
        }

        return entities.stream()
                .map(e -> ProjectEmployeeAssignment.restore(
                        e.getId().getEmployeeId(),
                        e.getAssignedAt()
                ))
                .collect(Collectors.toSet());
    }

    public void sync(ProjectEntity projectEntity, Set<ProjectEmployeeAssignment> desiredAssignments) {
        Set<ProjectEmployeeAssignment> desired =
                desiredAssignments == null ? Set.of() : desiredAssignments;

        Set<ProjectEmployeeEntity> current = projectEntity.getEmployees();
        UUID projectId = projectEntity.getId();

        Set<UUID> desiredEmployeeIds = desired.stream()
                .map(a -> a.getEmployeeId().value())
                .collect(Collectors.toSet());

        current.removeIf(e -> !desiredEmployeeIds.contains(e.getId().getEmployeeId()));

        Set<UUID> existingEmployeeIds = current.stream()
                .map(e -> e.getId().getEmployeeId())
                .collect(Collectors.toSet());

        for (ProjectEmployeeAssignment a : desired) {
            UUID employeeId = a.getEmployeeId().value();

            if (!existingEmployeeIds.contains(employeeId)) {
                current.add(ProjectEmployeeEntity.builder()
                        .id(new ProjectEmployeeId(projectId, employeeId))
                        .project(projectEntity)
                        .assignedAt(a.getAssignedAt())
                        .build());
            }
        }
    }
}
