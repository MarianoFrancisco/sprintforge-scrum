package com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.repository;

import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.entity.ProjectEntity;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

@NullMarked
public interface ProjectJpaRepository extends
        JpaRepository<ProjectEntity, UUID>,
        JpaSpecificationExecutor<ProjectEntity> {
    boolean existsByProjectKey(String projectKey);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, UUID id);

    Optional<ProjectEntity> findById(UUID uuid);
}
