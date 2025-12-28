package com.sprintforge.scrum.payment.application.mapper;

import com.sprintforge.scrum.payment.application.port.out.event.PaymentMadeIntegrationEvent;
import com.sprintforge.scrum.payment.domain.Payment;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PaymentIntegrationMapper {
    public PaymentMadeIntegrationEvent from(
            Payment payment
    ) {
        if (payment == null) {
            return null;
        }
        return new PaymentMadeIntegrationEvent(
                payment.getId().value(),
                payment.getProject().getId().value(),
                payment.getDate().value(),
                payment.getAmount().value(),
                payment.getMethod().name()
        );
    }
}
