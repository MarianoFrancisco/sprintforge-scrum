package com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.specification;

import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.entity.ProjectEmployeeEntity;
import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.entity.ProjectEntity;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.experimental.UtilityClass;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.UUID;

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

    public Specification<ProjectEntity> isClosed(Boolean isClosed) {
        return (root, ignored, cb) ->
                cb.equal(root.get("isClosed"), isClosed);
    }

    public Specification<ProjectEntity> notDeleted() {
        return (root, ignored, cb) ->
                cb.isFalse(root.get("isDeleted"));
    }

    public static Specification<ProjectEntity> hasEmployee(UUID employeeId) {
    return (root, query, cb) -> {
        Join<ProjectEntity, ProjectEmployeeEntity> employeesJoin = root.join("employees", JoinType.INNER);
        
        return cb.equal(employeesJoin.get("id").get("employeeId"), employeeId);
    };
}

    public Specification<ProjectEntity> withFilters(
            @Nullable String searchTerm,
            @Nullable Boolean isClosed
    ) {
        Specification<ProjectEntity> spec = (ignoredRoot, ignoredQuery, cb) -> cb.conjunction();

        spec = spec.and(notDeleted());

        if (isNotBlank(searchTerm)) {
            spec = spec.and(nameOrDescriptionOrClientOrAreaLike(searchTerm));
        }
        if (isClosed != null) {
            spec = spec.and(isClosed(isClosed));
        }

        return spec;
    }
}
