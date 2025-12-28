package com.sprintforge.scrum.workitem.infrastructure.adapter.out.persistence.specification;

import com.sprintforge.scrum.workitem.infrastructure.adapter.out.persistence.entity.WorkItemEntity;
import jakarta.validation.constraints.NotNull;
import lombok.experimental.UtilityClass;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

import static org.apache.commons.lang.StringUtils.isNotBlank;

@NullMarked
@UtilityClass
public class WorkItemSpecs {

    public Specification<WorkItemEntity> hasProject(@NotNull UUID projectId) {
        return (root, query, cb) -> {
            query.distinct(true);
            return cb.equal(root.get("project").get("id"), projectId);
        };
    }

    public Specification<WorkItemEntity> hasSprint(@NotNull UUID sprintId) {
        return (root, ignored, cb) ->
                cb.equal(root.get("sprint").get("id"), sprintId);
    }

    public Specification<WorkItemEntity> isBacklog() {
        return (root, ignored, cb) ->
                cb.isNull(root.get("sprint"));
    }

    public Specification<WorkItemEntity> hasBoardColumn(@NotNull UUID boardColumnId) {
        return (root, ignored, cb) ->
                cb.equal(root.get("boardColumn").get("id"), boardColumnId);
    }

    public Specification<WorkItemEntity> hasPriority(@NotNull Integer priority) {
        return (root, ignored, cb) ->
                cb.equal(root.get("priority"), priority);
    }

    public Specification<WorkItemEntity> hasDeveloper(@NotNull UUID developerId) {
        return (root, ignored, cb) ->
                cb.equal(root.get("developerId"), developerId);
    }

    public Specification<WorkItemEntity> hasProductOwner(@NotNull UUID productOwnerId) {
        return (root, ignored, cb) ->
                cb.equal(root.get("productOwnerId"), productOwnerId);
    }

    public Specification<WorkItemEntity> parametersLike(String searchTerm) {
        String pattern = "%" + searchTerm.toLowerCase() + "%";

        return (root, ignored, cb) ->
                cb.or(
                        cb.like(cb.lower(root.get("title")), pattern),
                        cb.like(cb.lower(root.get("description")), pattern),
                        cb.like(cb.lower(root.get("acceptanceCriteria")), pattern)
                );
    }

    public Specification<WorkItemEntity> notDeleted() {
        return (root, ignored, cb) ->
                cb.isFalse(root.get("isDeleted"));
    }

    public Specification<WorkItemEntity> withFilters(
            @NotNull UUID projectId,
            @Nullable UUID sprintId,
            @Nullable UUID boardColumnId,
            @Nullable Integer priority,
            @Nullable UUID developerId,
            @Nullable UUID productOwnerId,
            @Nullable String searchTerm
    ) {
        Specification<WorkItemEntity> spec =
                (ignoredRoot, ignoredQuery, cb) -> cb.conjunction();

        spec = spec.and(hasProject(projectId));

        spec = spec.and(notDeleted());

        if (sprintId != null) {
            spec = spec.and(hasSprint(sprintId));
        } else {
            spec = spec.and(isBacklog());
        }

        if (boardColumnId != null) {
            spec = spec.and(hasBoardColumn(boardColumnId));
        }

        if (priority != null) spec = spec.and(hasPriority(priority));
        if (developerId != null) spec = spec.and(hasDeveloper(developerId));
        if (productOwnerId != null) spec = spec.and(hasProductOwner(productOwnerId));
        if (isNotBlank(searchTerm)) spec = spec.and(parametersLike(searchTerm));

        return spec;
    }
}
