package com.sprintforge.scrum.payment.application.service;

import com.sprintforge.scrum.payment.application.port.in.query.GetAllPayments;
import com.sprintforge.scrum.payment.application.port.in.query.GetAllPaymentsQuery;
import com.sprintforge.scrum.payment.application.port.out.persistence.FindAllPayments;
import com.sprintforge.scrum.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetAllPaymentsImpl implements GetAllPayments {

    private final FindAllPayments findAllPayments;

    @Override
    public List<Payment> handle(GetAllPaymentsQuery query) {
        return findAllPayments.findAll(query);
    }
}
