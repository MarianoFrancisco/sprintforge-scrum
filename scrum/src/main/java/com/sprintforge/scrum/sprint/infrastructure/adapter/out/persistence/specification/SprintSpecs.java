package com.sprintforge.scrum.sprint.infrastructure.adapter.out.persistence.specification;

import com.sprintforge.scrum.sprint.domain.valueobject.SprintStatus;
import com.sprintforge.scrum.sprint.infrastructure.adapter.out.persistence.entity.SprintEntity;
import jakarta.validation.constraints.NotNull;
import lombok.experimental.UtilityClass;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

import static java.util.Locale.ROOT;
import static javax.xml.transform.OutputKeys.METHOD;
import static org.apache.commons.lang.StringUtils.isNotBlank;

@NullMarked
@UtilityClass
public class SprintSpecs {

    public Specification<SprintEntity> hasProject(@NotNull UUID projectId) {
        return (root, query, cb) -> {
            query.distinct(true);
            return cb.equal(root.get("project").get("id"), projectId);
        };
    }

    public Specification<SprintEntity> nameOrGoalLike(String searchTerm) {
        String pattern = "%" + searchTerm.toLowerCase() + "%";

        return (root, ignored, cb) ->
                cb.or(
                        cb.like(cb.lower(root.get("name")), pattern),
                        cb.like(cb.lower(root.get("goal")), pattern)
                );
    }

    public Specification<SprintEntity> withStatus(SprintStatus status) {
        return (root, ignored, cb) ->
                cb.equal(root.get(METHOD), status);
    }

    public Specification<SprintEntity> withStatus(String status) {
        if (!isNotBlank(status)) {
            return (ignoredRoot, ignoredQuery, cb) -> cb.conjunction();
        }

        SprintStatus parsed;
        try {
            parsed = SprintStatus.valueOf(status.trim().toUpperCase(ROOT));
        } catch (IllegalArgumentException ex) {
            return (ignoredRoot, ignoredQuery, cb) -> cb.conjunction();
        }

        return withStatus(parsed);
    }

    public Specification<SprintEntity> notDeleted() {
        return (root, ignored, cb) ->
                cb.isFalse(root.get("isDeleted"));
    }

    public Specification<SprintEntity> withFilters(
            @NotNull UUID projectId,
            @Nullable String searchTerm,
            @Nullable String status
    ) {
        Specification<SprintEntity> spec = (ignoredRoot, ignoredQuery, cb) -> cb.conjunction();

        spec = spec.and(hasProject(projectId));

        spec = spec.and(notDeleted());

        if (isNotBlank(searchTerm)) {
            spec = spec.and(nameOrGoalLike(searchTerm));
        }
        if (isNotBlank(status)) {
            spec = spec.and(withStatus(status));
        }

        return spec;
    }
}
