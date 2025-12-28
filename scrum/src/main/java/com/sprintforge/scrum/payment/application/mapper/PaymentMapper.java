package com.sprintforge.scrum.payment.application.mapper;

import com.sprintforge.scrum.payment.application.port.in.command.MakePaymentCommand;
import com.sprintforge.scrum.payment.domain.Payment;
import com.sprintforge.scrum.project.domain.Project;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PaymentMapper {
    public Payment toDomain(
            MakePaymentCommand command,
            Project project
    ) {
        if (command == null) {
            return null;
        }
        return new Payment(
                command.date(),
                command.amount(),
                command.method(),
                command.reference(),
                command.note(),
                project
        );
    }
}
