package com.sprintforge.scrum.payment.application.service;

import com.sprintforge.scrum.payment.application.mapper.PaymentMapper;
import com.sprintforge.scrum.payment.application.port.in.command.CreatePayment;
import com.sprintforge.scrum.payment.application.port.in.command.CreatePaymentCommand;
import com.sprintforge.scrum.payment.application.port.out.persistence.SavePayment;
import com.sprintforge.scrum.payment.domain.Payment;
import com.sprintforge.scrum.project.application.port.in.query.GetProjectById;
import com.sprintforge.scrum.project.application.port.in.query.GetProjectByIdQuery;
import com.sprintforge.scrum.project.domain.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class PaymentMadeImpl implements CreatePayment {

    private final GetProjectById getProjectById;
    private final SavePayment savePayment;

    @Override
    public Payment handle(CreatePaymentCommand command) {
        Project project = getProjectById.handle(new GetProjectByIdQuery(command.projectId()));
        Payment payment = PaymentMapper.toDomain(command, project);
        Payment savedPayment = savePayment.save(payment);
        return savedPayment;
    }
}
