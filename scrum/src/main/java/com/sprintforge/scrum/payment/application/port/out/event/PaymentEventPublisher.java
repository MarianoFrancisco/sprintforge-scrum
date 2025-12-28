package com.sprintforge.scrum.payment.application.port.out.event;

public interface PaymentEventPublisher {
    void publishPaymentMade(PaymentMadeIntegrationEvent event);
}
