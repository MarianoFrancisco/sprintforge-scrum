package com.sprintforge.scrum.payment.application.port.in.query;

import java.util.UUID;

public record GetPaymentByIdQuery(
        UUID id
) {
}
