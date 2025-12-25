package com.sprintforge.scrum.payment.domain;

import com.sprintforge.common.domain.exception.ValidationException;
import com.sprintforge.scrum.payment.domain.valueobject.*;
import com.sprintforge.scrum.project.domain.Project;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

@Getter
public class Payment {

    private final PaymentId id;

    private PaymentDate date;
    private PaymentAmount amount;
    private PaymentMethod method;

    private PaymentReference reference;
    private PaymentNote note;

    private final Instant createdAt;

    private Project project;

    public Payment(
            LocalDate date,
            BigDecimal amount,
            String method,
            String reference,
            String note,
            Project project
    ) {
        this.id = PaymentId.of(randomUUID());

        this.date = PaymentDate.of(date);
        this.amount = PaymentAmount.of(amount);
        this.method = PaymentMethod.safeValueOf(method);

        this.reference = PaymentReference.of(reference);
        this.note = PaymentNote.of(note);

        this.createdAt = now();

        this.project = validateProject(project);
    }

    public Payment(
            UUID id,
            LocalDate date,
            BigDecimal amount,
            PaymentMethod method,
            String reference,
            String note,
            Instant createdAt,
            Project project
    ) {
        this.id = PaymentId.of(id);

        this.date = PaymentDate.of(date);
        this.amount = PaymentAmount.of(amount);
        this.method = method;

        this.reference = PaymentReference.of(reference);
        this.note = PaymentNote.of(note);

        this.createdAt = createdAt;

        this.project = project;
    }

    private Project validateProject(Project project) {
        if (project == null) {
            throw new ValidationException("El proyecto no puede estar vacío");
        }
        project.validateActive();
        return project;
    }
}
