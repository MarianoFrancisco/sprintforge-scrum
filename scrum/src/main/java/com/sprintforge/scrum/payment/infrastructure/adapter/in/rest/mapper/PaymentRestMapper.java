package com.sprintforge.scrum.payment.infrastructure.adapter.in.rest.mapper;

import com.sprintforge.scrum.payment.application.port.in.command.CreatePaymentCommand;
import com.sprintforge.scrum.payment.application.port.in.query.GetAllPaymentsQuery;
import com.sprintforge.scrum.payment.application.port.in.query.GetPaymentByIdQuery;
import com.sprintforge.scrum.payment.domain.Payment;
import com.sprintforge.scrum.payment.infrastructure.adapter.in.rest.dto.CreatePaymentRequestDTO;
import com.sprintforge.scrum.payment.infrastructure.adapter.in.rest.dto.PaymentResponseDTO;
import com.sprintforge.scrum.project.infrastructure.adapter.in.rest.mapper.ProjectRestMapper;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.UUID;

@UtilityClass
public class PaymentRestMapper {
    public GetAllPaymentsQuery toQuery(
            String searchTerm,
            UUID projectId,
            String method,
            LocalDate fromDate,
            LocalDate toDate
    ) {
        return new GetAllPaymentsQuery(
                searchTerm,
                projectId,
                method,
                fromDate,
                toDate
        );
    }

    public CreatePaymentCommand to(CreatePaymentRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return new CreatePaymentCommand(
                dto.employeeId(),
                dto.projectId(),
                dto.date(),
                dto.amount(),
                dto.method(),
                dto.reference(),
                dto.note()
        );
    }

    public PaymentResponseDTO toResponse(Payment payment) {
        if (payment == null) {
            return null;
        }
        return new PaymentResponseDTO(
                payment.getId().value(),
                payment.getDate().value(),
                payment.getAmount().value(),
                payment.getMethod().name(),
                payment.getReference() != null ? payment.getReference().value() : null,
                payment.getNote() != null ? payment.getNote().value() : null,
                payment.getCreatedAt(),
                ProjectRestMapper.toResponse(payment.getProject())
        );
    }

    public GetPaymentByIdQuery toQueryById(UUID id) {
        return new GetPaymentByIdQuery(id);
    }
}
