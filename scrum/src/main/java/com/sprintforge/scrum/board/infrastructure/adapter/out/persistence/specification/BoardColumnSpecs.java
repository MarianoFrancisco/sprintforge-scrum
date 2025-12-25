package com.sprintforge.scrum.board.infrastructure.adapter.out.persistence.specification;

import com.sprintforge.scrum.board.infrastructure.adapter.out.persistence.entity.BoardColumnEntity;
import jakarta.validation.constraints.NotNull;
import lombok.experimental.UtilityClass;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

import static org.apache.commons.lang.StringUtils.isNotBlank;

@NullMarked
@UtilityClass
public class BoardColumnSpecs {

    public Specification<BoardColumnEntity> nameLike(String searchTerm) {
        String pattern = "%" + searchTerm.toLowerCase() + "%";

        return (root, ignored, cb) ->
                cb.or(
                        cb.like(cb.lower(root.get("name")), pattern)
                );
    }

    public Specification<BoardColumnEntity> hasSprint(@NotNull UUID sprintId) {
        return (root, query, cb) -> {
            query.distinct(true);
            return cb.equal(root.get("sprint").get("id"), sprintId);
        };
    }

    public Specification<BoardColumnEntity> notDeleted() {
        return (root, ignored, cb) ->
                cb.isFalse(root.get("isDeleted"));
    }

    public Specification<BoardColumnEntity> withFilters(
            @NotNull UUID sprintId,
            @Nullable String searchTerm
    ) {
        Specification<BoardColumnEntity> spec = (ignoredRoot, ignoredQuery, cb) -> cb.conjunction();

        spec = spec.and(hasSprint(sprintId));

        spec = spec.and(notDeleted());

        if (isNotBlank(searchTerm)) {
            spec = spec.and(nameLike(searchTerm));
        }

        return spec;
    }
}
