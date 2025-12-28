package com.sprintforge.scrum.project.infrastructure.adapter.out.persistence;

import com.sprintforge.scrum.project.application.port.in.query.GetAllProjectsQuery;
import com.sprintforge.scrum.project.application.port.out.persistence.*;
import com.sprintforge.scrum.project.domain.Project;
import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.entity.ProjectEntity;
import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.mapper.ProjectEntityMapper;
import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.repository.ProjectJpaRepository;
import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.specification.ProjectSpecs;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NullMarked
@Repository
@RequiredArgsConstructor
public class ProjectRepository implements
        ExistProjectByProjectKey,
        ExistProjectByName,
        ExistsProjectByNameAndIdNot,
        FindAllProjects,
        FindProjectById,
        SaveProject {

    private final ProjectJpaRepository projectJpaRepository;

    @Override
    public boolean existsByProjectKey(String projectKey) {
        return projectJpaRepository.existsByProjectKey(projectKey);
    }

    @Override
    public boolean existsByName(String name) {
        return projectJpaRepository.existsByName(name);
    }

    @Override
    public boolean existsByNameAndIdNot(String name, UUID id) {
        return projectJpaRepository.existsByNameAndIdNot(name, id);
    }

    @Override
    public List<Project> findAll(GetAllProjectsQuery query) {
        Specification<ProjectEntity> spec = ProjectSpecs.withFilters(
                query.searchTerm(),
                query.isClosed()
        );

        return projectJpaRepository.findAll(spec)
                .stream()
                .map(ProjectEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Project> findById(UUID id) {
        return projectJpaRepository.findById(id).map(
                ProjectEntityMapper::toDomain
        );
    }

    @Override
    public Project save(Project project) {
        ProjectEntity existing = projectJpaRepository.findById(project.getId().value()).orElse(null);
        ProjectEntity entity = ProjectEntityMapper.toEntity(project, existing);
        ProjectEntity saved = projectJpaRepository.save(entity);
        return ProjectEntityMapper.toDomain(saved);
    }
}
