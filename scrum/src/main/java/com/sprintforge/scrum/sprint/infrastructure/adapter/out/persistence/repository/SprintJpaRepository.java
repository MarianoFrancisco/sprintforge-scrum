package com.sprintforge.scrum.sprint.infrastructure.adapter.out.persistence.repository;

import com.sprintforge.scrum.sprint.infrastructure.adapter.out.persistence.entity.SprintEntity;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

@NullMarked
public interface SprintJpaRepository extends
        JpaRepository<SprintEntity, UUID>,
        JpaSpecificationExecutor<SprintEntity> {
    Optional<SprintEntity> findById(UUID uuid);
}
