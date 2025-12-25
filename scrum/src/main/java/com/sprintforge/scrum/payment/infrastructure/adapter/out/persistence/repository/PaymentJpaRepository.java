package com.sprintforge.scrum.payment.infrastructure.adapter.out.persistence.repository;

import com.sprintforge.scrum.payment.infrastructure.adapter.out.persistence.entity.PaymentEntity;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

@NullMarked
public interface PaymentJpaRepository extends
        JpaRepository<PaymentEntity, UUID>,
        JpaSpecificationExecutor<PaymentEntity> {
    Optional<PaymentEntity> findById(UUID uuid);
}
