package com.sprintforge.scrum.payment.infrastructure.adapter.out.persistence.specification;

import com.sprintforge.scrum.payment.domain.valueobject.PaymentMethod;
import com.sprintforge.scrum.payment.infrastructure.adapter.out.persistence.entity.PaymentEntity;
import lombok.experimental.UtilityClass;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

import static java.util.Locale.ROOT;
import static org.apache.commons.lang.StringUtils.isNotBlank;

@NullMarked
@UtilityClass
public class PaymentSpecs {

    private static final String PROJECT = "project";
    private static final String ID = "id";
    private static final String DATE = "date";
    private static final String METHOD = "method";
    private static final String REFERENCE = "reference";
    private static final String NOTE = "note";

    public Specification<PaymentEntity> searchLike(String searchTerm) {
        if (!isNotBlank(searchTerm)) {
            return (ignoredRoot, ignoredQuery, cb) -> cb.conjunction();
        }

        String pattern = "%" + searchTerm.toLowerCase(ROOT) + "%";

        return (root, ignored, cb) ->
                cb.or(
                        cb.like(cb.lower(root.get(REFERENCE)), pattern),
                        cb.like(cb.lower(root.get(NOTE)), pattern)
                );
    }

    public Specification<PaymentEntity> withProjectId(UUID projectId) {
        return (root, ignored, cb) ->
                cb.equal(root.get(PROJECT).get(ID), projectId);
    }

    public Specification<PaymentEntity> withMethod(PaymentMethod method) {
        return (root, ignored, cb) ->
                cb.equal(root.get(METHOD), method);
    }

    public Specification<PaymentEntity> withMethod(String method) {
        if (!isNotBlank(method)) {
            return (ignoredRoot, ignoredQuery, cb) -> cb.conjunction();
        }

        PaymentMethod parsed;
        try {
            parsed = PaymentMethod.valueOf(method.trim().toUpperCase(ROOT));
        } catch (IllegalArgumentException ex) {
            return (ignoredRoot, ignoredQuery, cb) -> cb.conjunction();
        }

        return withMethod(parsed);
    }

    public Specification<PaymentEntity> fromDate(LocalDate fromDate) {
        return (root, ignored, cb) ->
                cb.greaterThanOrEqualTo(root.get(DATE), fromDate);
    }

    public Specification<PaymentEntity> toDate(LocalDate toDate) {
        return (root, ignored, cb) ->
                cb.lessThanOrEqualTo(root.get(DATE), toDate);
    }

    public Specification<PaymentEntity> withFilters(
            @Nullable String searchTerm,
            @Nullable UUID projectId,
            @Nullable String method,
            @Nullable LocalDate fromDate,
            @Nullable LocalDate toDate
    ) {
        Specification<PaymentEntity> spec =
                (ignoredRoot, ignoredQuery, cb) -> cb.conjunction();

        if (projectId != null) {
            spec = spec.and(withProjectId(projectId));
        }

        if (isNotBlank(searchTerm)) {
            spec = spec.and(searchLike(searchTerm));
        }

        if (isNotBlank(method)) {
            spec = spec.and(withMethod(method));
        }

        LocalDate effectiveFrom = fromDate;
        LocalDate effectiveTo = toDate;

        if (effectiveFrom != null && effectiveTo != null && effectiveFrom.isAfter(effectiveTo)) {
            LocalDate tmp = effectiveFrom;
            effectiveFrom = effectiveTo;
            effectiveTo = tmp;
        }

        if (effectiveFrom != null) {
            spec = spec.and(fromDate(effectiveFrom));
        }

        if (effectiveTo != null) {
            spec = spec.and(toDate(effectiveTo));
        }

        return spec;
    }
}
