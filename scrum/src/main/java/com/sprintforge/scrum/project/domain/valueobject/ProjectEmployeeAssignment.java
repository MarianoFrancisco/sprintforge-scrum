package com.sprintforge.scrum.project.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

import static java.time.Instant.now;

@Getter
@EqualsAndHashCode(of = "employeeId")
public final class ProjectEmployeeAssignment {

    private final EmployeeId employeeId;
    private final Instant assignedAt;

    private ProjectEmployeeAssignment(EmployeeId employeeId, Instant assignedAt) {
        this.employeeId = employeeId;
        this.assignedAt = assignedAt == null ? now() : assignedAt;
    }

    public static ProjectEmployeeAssignment of(UUID employeeId) {
        return new ProjectEmployeeAssignment(EmployeeId.of(employeeId), now());
    }

    public static ProjectEmployeeAssignment of(EmployeeId employeeId) {
        return new ProjectEmployeeAssignment(employeeId, now());
    }

    public static ProjectEmployeeAssignment restore(UUID employeeId, Instant assignedAt) {
        return new ProjectEmployeeAssignment(EmployeeId.of(employeeId), assignedAt);
    }
}
