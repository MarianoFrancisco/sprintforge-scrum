package com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.mapper;

import com.sprintforge.scrum.project.domain.Project;
import com.sprintforge.scrum.project.domain.valueobject.ProjectEmployeeAssignment;
import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.entity.ProjectEntity;
import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class ProjectEntityMapper {

    public Project toDomain(ProjectEntity entity) {
        if (entity == null) {
            return null;
        }

        Set<ProjectEmployeeAssignment> assignments =
                ProjectEmployeeEntityMapper.toDomain(entity.getEmployees());

        return new Project(
                entity.getId(),
                entity.getProjectKey(),
                entity.getName(),
                entity.getDescription(),
                entity.getClient(),
                entity.getArea(),
                entity.getBudgetAmount(),
                entity.getContractAmount(),
                entity.isClosed(),
                entity.isDeleted(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                assignments
        );
    }

    public ProjectEntity toEntity(Project project, ProjectEntity target) {
        if (project == null) {
            return null;
        }

        ProjectEntity entity = target != null ? target : new ProjectEntity();

        entity.setId(project.getId().value());
        entity.setProjectKey(project.getProjectKey().value());
        entity.setName(project.getName().value());
        entity.setDescription(project.getDescription() != null ? project.getDescription().value() : null);
        entity.setClient(project.getClient().value());
        entity.setArea(project.getArea().value());
        entity.setBudgetAmount(project.getBudgetAmount() != null ? project.getBudgetAmount().value() : null);
        entity.setContractAmount(project.getContractAmount() != null ? project.getContractAmount().value() : null);
        entity.setClosed(project.isClosed());
        entity.setDeleted(project.isDeleted());
        entity.setCreatedAt(project.getCreatedAt());
        entity.setUpdatedAt(project.getUpdatedAt());

        if (entity.getEmployees() == null) {
            entity.setEmployees(new HashSet<>());
        }

        ProjectEmployeeEntityMapper.sync(entity, project.getAssignments());

        return entity;
    }

    public ProjectEntity toEntity(Project project) {
        return toEntity(project, null);
    }
}
