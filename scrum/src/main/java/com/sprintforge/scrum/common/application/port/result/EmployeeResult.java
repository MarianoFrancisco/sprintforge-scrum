package com.sprintforge.scrum.common.application.port.result;

import java.util.UUID;

public record EmployeeResult(
        UUID id,
        String email,
        String fullName,
        String profileImage,
        String position
) {
}
