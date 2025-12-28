package com.sprintforge.scrum.project.domain;

import com.sprintforge.common.domain.exception.ValidationException;
import com.sprintforge.scrum.project.domain.valueobject.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

@Getter
public class Project {

    private final ProjectId id;
    private final ProjectKey projectKey;
    private ProjectName name;
    private ProjectDescription description;

    private ProjectClient client;
    private ProjectArea area;

    private ProjectBudgetAmount budgetAmount;
    private ProjectContractAmount contractAmount;

    private boolean isClosed;
    private boolean isDeleted;

    private final Instant createdAt;
    private Instant updatedAt;

    private final Set<ProjectEmployeeAssignment> assignments = new HashSet<>();

    public Project(
            String projectKey,
            String name,
            String description,
            String client,
            String area,
            BigDecimal budgetAmount,
            BigDecimal contractAmount
    ) {
        Instant now = now();

        this.id = ProjectId.of(randomUUID());
        this.projectKey = ProjectKey.of(projectKey);
        this.name = ProjectName.of(name);
        this.description = ProjectDescription.of(description);

        this.client = ProjectClient.of(client);
        this.area = ProjectArea.of(area);
        this.budgetAmount = ProjectBudgetAmount.of(budgetAmount);
        this.contractAmount = ProjectContractAmount.of(contractAmount);

        this.isClosed = false;
        this.isDeleted = false;

        this.createdAt = now;
        this.updatedAt = now;
    }

    public Project(
            UUID id,
            String projectKey,
            String name,
            String description,
            String client,
            String area,
            BigDecimal budgetAmount,
            BigDecimal contractAmount,
            boolean isClosed,
            boolean isDeleted,
            Instant createdAt,
            Instant updatedAt,
            Set<ProjectEmployeeAssignment> assignments
    ) {
        this.id = ProjectId.of(id);
        this.projectKey = ProjectKey.of(projectKey);
        this.name = ProjectName.of(name);
        this.description = ProjectDescription.of(description);
        this.client = ProjectClient.of(client);
        this.area = ProjectArea.of(area);
        this.budgetAmount = ProjectBudgetAmount.of(budgetAmount);
        this.contractAmount = ProjectContractAmount.of(contractAmount);
        this.isClosed = isClosed;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        if (assignments != null) {
            this.assignments.addAll(assignments);
        }
    }

    public void updateName(String newName) {
        performUpdate();
        this.name = ProjectName.of(newName);
    }

    public void updateDescription(String newDescription) {
        performUpdate();
        this.description = ProjectDescription.of(newDescription);
    }

    public void updateClient(String newClient) {
        performUpdate();
        this.client = ProjectClient.of(newClient);
    }

    public void updateArea(String newArea) {
        validateNotDeleted();
        this.area = ProjectArea.of(newArea);
    }

    public void updateAmounts(BigDecimal newBudgetAmount, BigDecimal newContractAmount) {
        performUpdate();
        this.budgetAmount = ProjectBudgetAmount.of(newBudgetAmount);
        this.contractAmount = ProjectContractAmount.of(newContractAmount);
    }

    public void setEmployees(Set<UUID> employeeIds) {
        performUpdate();

        Set<EmployeeId> desired = employeeIds.stream()
                .map(EmployeeId::of)
                .collect(java.util.stream.Collectors.toSet());

        assignments.removeIf(a -> !desired.contains(a.getEmployeeId()));

        for (EmployeeId eid : desired) {
            boolean exists = assignments.stream().anyMatch(a -> a.getEmployeeId().equals(eid));
            if (!exists) {
                assignments.add(ProjectEmployeeAssignment.of(eid));
            }
        }
    }

    public void assignEmployees(Set<UUID> employeeIds) {
        performUpdate();

        Set<EmployeeId> ids = employeeIds.stream()
                .map(EmployeeId::of)
                .collect(java.util.stream.Collectors.toSet());

        for (EmployeeId eid : ids) {
            boolean exists = assignments.stream().anyMatch(a -> a.getEmployeeId().equals(eid));
            if (exists) {
                throw new ValidationException("Uno o más empleados ya están asignados al proyecto");
            }
        }

        for (EmployeeId eid : ids) {
            assignments.add(ProjectEmployeeAssignment.of(eid));
        }
    }

    public void unassignEmployees(Set<UUID> employeeIds) {
        performUpdate();

        Set<EmployeeId> ids = employeeIds.stream()
                .map(EmployeeId::of)
                .collect(java.util.stream.Collectors.toSet());

        boolean removedAny = assignments.removeIf(a -> ids.contains(a.getEmployeeId()));
        if (!removedAny) {
            throw new ValidationException("Ninguno de los empleados indicados está asignado al proyecto");
        }
    }

    public Set<ProjectEmployeeAssignment> getAssignments() {
        return Set.copyOf(assignments);
    }

    public void close() {
        if (this.isClosed) {
            throw new ValidationException("El proyecto ya se encuentra cerrado");
        }
        this.isClosed = true;
        this.updatedAt = now();
    }

    public void open() {
        if (!this.isClosed) {
            throw new ValidationException("El proyecto ya se encuentra abierto");
        }
        this.isClosed = false;
        this.updatedAt = now();
    }

    public void delete() {
        if (this.isDeleted) {
            throw new ValidationException("El proyecto ya se encuentra eliminado");
        }
        this.isDeleted = true;
        this.updatedAt = now();
    }

    private void validateNotDeleted() {
        if (this.isDeleted) {
            throw new ValidationException("No se puede operar sobre un proyecto eliminado");
        }
    }

    public void validateNotClosed() {
        if (this.isClosed) {
            throw new ValidationException("No se puede operar sobre un proyecto cerrado");
        }
    }

    public void validateActive() {
        validateNotDeleted();
        validateNotClosed();
    }

    private void performUpdate() {
        validateActive();
        this.updatedAt = now();
    }
}
