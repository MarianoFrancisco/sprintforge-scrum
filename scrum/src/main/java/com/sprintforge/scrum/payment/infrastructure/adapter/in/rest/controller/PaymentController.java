package com.sprintforge.scrum.payment.infrastructure.adapter.in.rest.controller;

import com.sprintforge.scrum.payment.application.port.in.command.MakePayment;
import com.sprintforge.scrum.payment.application.port.in.query.GetAllPayments;
import com.sprintforge.scrum.payment.application.port.in.query.GetPaymentById;
import com.sprintforge.scrum.payment.domain.Payment;
import com.sprintforge.scrum.payment.infrastructure.adapter.in.rest.dto.CreatePaymentRequestDTO;
import com.sprintforge.scrum.payment.infrastructure.adapter.in.rest.dto.PaymentResponseDTO;
import com.sprintforge.scrum.payment.infrastructure.adapter.in.rest.mapper.PaymentRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final GetAllPayments getAllPayments;
    private final GetPaymentById getPaymentById;
    private final MakePayment createPayment;

    @GetMapping
    public List<PaymentResponseDTO> getAll(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) UUID projectId,
            @RequestParam(required = false) String method,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DATE) LocalDate fromDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DATE) LocalDate toDate
    ) {
        List<Payment> payments = getAllPayments.handle(
                PaymentRestMapper.toQuery(
                        searchTerm,
                        projectId,
                        method,
                        fromDate,
                        toDate
                )
        );
        return payments.stream()
                .map(PaymentRestMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public PaymentResponseDTO getById(@PathVariable UUID id) {
        Payment payment = getPaymentById.handle(
                PaymentRestMapper.toQueryById(id)
        );
        return PaymentRestMapper.toResponse(payment);
    }

    @PostMapping
    @ResponseStatus(code = CREATED)
    public PaymentResponseDTO create(
            @RequestBody @Valid CreatePaymentRequestDTO dto
    ) {
        Payment payment = createPayment.handle(
                PaymentRestMapper.to(
                        dto
                )
        );
        return PaymentRestMapper.toResponse(payment);
    }
}
