package com.sprintforge.scrum.payment.infrastructure.adapter.out.persistence.mapper;

import com.sprintforge.scrum.payment.domain.Payment;
import com.sprintforge.scrum.payment.infrastructure.adapter.out.persistence.entity.PaymentEntity;
import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.mapper.ProjectEntityMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PaymentEntityMapper {
    public Payment toDomain(PaymentEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Payment(
                entity.getId(),
                entity.getDate(),
                entity.getAmount(),
                entity.getMethod(),
                entity.getReference(),
                entity.getNote(),
                entity.getCreatedAt(),
                ProjectEntityMapper.toDomain(entity.getProject())
        );
    }

    public PaymentEntity toEntity(Payment domain) {
        if (domain == null) {
            return null;
        }

        return PaymentEntity.builder()
                .id(domain.getId().value())
                .date(domain.getDate().value())
                .amount(domain.getAmount().value())
                .method(domain.getMethod())
                .reference(domain.getReference() != null ? domain.getReference().value() : null)
                .note(domain.getNote() != null ? domain.getNote().value() : null)
                .createdAt(domain.getCreatedAt())
                .project(ProjectEntityMapper.toEntity(domain.getProject()))
                .build();
    }
}
