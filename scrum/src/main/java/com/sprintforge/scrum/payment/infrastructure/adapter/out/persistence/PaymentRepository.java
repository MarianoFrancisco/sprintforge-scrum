package com.sprintforge.scrum.payment.infrastructure.adapter.out.persistence;

import com.sprintforge.scrum.payment.application.port.in.query.GetAllPaymentsQuery;
import com.sprintforge.scrum.payment.application.port.out.persistence.FindAllPayments;
import com.sprintforge.scrum.payment.application.port.out.persistence.FindPaymentById;
import com.sprintforge.scrum.payment.application.port.out.persistence.SavePayment;
import com.sprintforge.scrum.payment.domain.Payment;
import com.sprintforge.scrum.payment.infrastructure.adapter.out.persistence.entity.PaymentEntity;
import com.sprintforge.scrum.payment.infrastructure.adapter.out.persistence.mapper.PaymentEntityMapper;
import com.sprintforge.scrum.payment.infrastructure.adapter.out.persistence.repository.PaymentJpaRepository;
import com.sprintforge.scrum.payment.infrastructure.adapter.out.persistence.specification.PaymentSpecs;
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
public class PaymentRepository implements
        FindAllPayments,
        FindPaymentById,
        SavePayment {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public List<Payment> findAll(GetAllPaymentsQuery query) {

        Specification<PaymentEntity> spec = PaymentSpecs.withFilters(
                query.searchTerm(),
                query.projectId(),
                query.method(),
                query.fromDate(),
                query.toDate()
        );

        return paymentJpaRepository.findAll(spec)
                .stream()
                .map(PaymentEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Payment> findById(UUID id) {
        return paymentJpaRepository.findById(id).map(
                PaymentEntityMapper::toDomain
        );
    }

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = PaymentEntityMapper.toEntity(payment);
        PaymentEntity savedEntity = paymentJpaRepository.save(entity);
        return PaymentEntityMapper.toDomain(savedEntity);
    }
}
