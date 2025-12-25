package com.sprintforge.scrum.payment.application.service;

import com.sprintforge.scrum.payment.application.exception.PaymentNotFoundException;
import com.sprintforge.scrum.payment.application.port.in.query.GetPaymentById;
import com.sprintforge.scrum.payment.application.port.in.query.GetPaymentByIdQuery;
import com.sprintforge.scrum.payment.application.port.out.persistence.FindPaymentById;
import com.sprintforge.scrum.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetPaymentByIdImpl implements GetPaymentById {

    private final FindPaymentById findPaymentById;

    @Override
    public Payment handle(GetPaymentByIdQuery query) {
        return findPaymentById.findById(query.id()).orElseThrow(
                () -> new PaymentNotFoundException(query.id())
        );
    }
}
