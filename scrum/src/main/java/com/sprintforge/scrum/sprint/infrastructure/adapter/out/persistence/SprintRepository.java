package com.sprintforge.scrum.sprint.infrastructure.adapter.out.persistence;

import com.sprintforge.scrum.sprint.application.port.in.query.GetAllSprintsQuery;
import com.sprintforge.scrum.sprint.application.port.out.persistence.FindAllSprints;
import com.sprintforge.scrum.sprint.application.port.out.persistence.FindSprintById;
import com.sprintforge.scrum.sprint.application.port.out.persistence.SaveSprint;
import com.sprintforge.scrum.sprint.domain.Sprint;
import com.sprintforge.scrum.sprint.infrastructure.adapter.out.persistence.entity.SprintEntity;
import com.sprintforge.scrum.sprint.infrastructure.adapter.out.persistence.mapper.SprintEntityMapper;
import com.sprintforge.scrum.sprint.infrastructure.adapter.out.persistence.repository.SprintJpaRepository;
import com.sprintforge.scrum.sprint.infrastructure.adapter.out.persistence.specification.SprintSpecs;
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
public class SprintRepository implements
        FindAllSprints,
        FindSprintById,
        SaveSprint {

    private final SprintJpaRepository sprintJpaRepository;

    @Override
    public List<Sprint> findAll(GetAllSprintsQuery query) {
        Specification<SprintEntity> spec = SprintSpecs.withFilters(
                query.projectId(),
                query.searchTerm(),
                query.status()
        );

        return sprintJpaRepository.findAll(spec)
                .stream()
                .map(SprintEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Sprint> findById(UUID id) {
        return sprintJpaRepository.findById(id).map(
                SprintEntityMapper::toDomain
        );
    }

    @Override
    public Sprint save(Sprint sprint) {
        SprintEntity entity = SprintEntityMapper.toEntity(sprint);
        SprintEntity savedEntity = sprintJpaRepository.save(entity);
        return SprintEntityMapper.toDomain(savedEntity);
    }
}
