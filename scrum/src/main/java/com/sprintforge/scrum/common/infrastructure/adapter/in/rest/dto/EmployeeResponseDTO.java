package com.sprintforge.scrum.common.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record EmployeeResponseDTO(
        UUID id,
        String cui,
        String email,

        String firstName,
        String lastName,
        String fullName,

        String phoneNumber,
        LocalDate birthDate,

        EmployeeWorkloadTypeResponse workloadType,

        BigDecimal salary,

        String profileImage,

        EmployeeStatusResponse status,

        EmployeePositionResponseDTO position
) {
}
