package com.sprintforge.scrum.payment.infrastructure.adapter.out.messaging.kafka.mapper;

import com.sprintforge.scrum.payment.application.port.out.event.PaymentMadeIntegrationEvent;
import com.sprintforge.scrum.payment.infrastructure.adapter.out.messaging.kafka.event.PaymentMadeKafkaMessage;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PaymentKafkaEventMapper {

    public PaymentMadeKafkaMessage toMessage(PaymentMadeIntegrationEvent event) {
        return new PaymentMadeKafkaMessage(
                event.paymentId(),
                event.projectId(),
                event.date(),
                event.amount(),
                event.method()
        );
    }
}
