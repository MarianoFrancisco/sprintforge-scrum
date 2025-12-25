package com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.specification;

import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.entity.ProjectEntity;
import lombok.experimental.UtilityClass;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import static org.apache.commons.lang.StringUtils.isNotBlank;

@NullMarked
@UtilityClass
public class ProjectSpecs {

    public Specification<ProjectEntity> nameOrDescriptionOrClientOrAreaLike(String searchTerm) {
        String pattern = "%" + searchTerm.toLowerCase() + "%";

        return (root, ignored, cb) ->
                cb.or(
                        cb.like(cb.lower(root.get("name")), pattern),
                        cb.like(cb.lower(root.get("description")), pattern),
                        cb.like(cb.lower(root.get("client")), pattern),
                        cb.like(cb.lower(root.get("area")), pattern)
                );
    }

    public Specification<ProjectEntity> isActive(Boolean isActive) {
        return (root, ignored, cb) ->
                cb.equal(root.get("isActive"), isActive);
    }

    public Specification<ProjectEntity> notDeleted() {
        return (root, ignored, cb) ->
                cb.isFalse(root.get("isDeleted"));
    }

    public Specification<ProjectEntity> withFilters(
            @Nullable String searchTerm,
            @Nullable Boolean isActive
    ) {
        Specification<ProjectEntity> spec = (ignoredRoot, ignoredQuery, cb) -> cb.conjunction();

        spec = spec.and(notDeleted());

        if (isNotBlank(searchTerm)) {
            spec = spec.and(nameOrDescriptionOrClientOrAreaLike(searchTerm));
        }
        if (isActive != null) {
            spec = spec.and(isActive(isActive));
        }

        return spec;
    }
}
