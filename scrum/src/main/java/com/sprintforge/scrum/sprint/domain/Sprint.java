package com.sprintforge.scrum.sprint.domain;

import com.sprintforge.common.domain.exception.ValidationException;
import com.sprintforge.scrum.project.domain.Project;
import com.sprintforge.scrum.sprint.domain.valueobject.*;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

@Getter
public class Sprint {

    private final SprintId id;

    private SprintName name;
    private SprintGoal goal;

    private SprintStatus status;

    private SprintStartDate startDate;
    private SprintEndDate endDate;

    private boolean isDeleted;

    private final Instant createdAt;
    private Instant updatedAt;

    private Project project;

    public Sprint(
            String name,
            String goal,
            Instant startDate,
            Instant endDate,
            Project project
    ) {
        Instant now = now();

        this.id = SprintId.of(randomUUID());

        this.name = SprintName.of(name);
        this.goal = SprintGoal.of(goal);

        this.startDate = SprintStartDate.of(startDate);
        this.endDate = SprintEndDate.of(endDate);

        validateDateRange(this.startDate, this.endDate);

        this.status = SprintStatus.CREATED;
        this.isDeleted = false;

        this.createdAt = now;
        this.updatedAt = now;

        this.project = validateProject(project);
    }

    public Sprint(
            UUID id,
            String name,
            String goal,
            SprintStatus status,
            Instant startDate,
            Instant endDate,
            boolean isDeleted,
            Instant createdAt,
            Instant updatedAt,
            Project project
    ) {
        this.id = SprintId.of(id);

        this.name = SprintName.of(name);
        this.goal = SprintGoal.of(goal);

        this.status = status;

        this.startDate = SprintStartDate.of(startDate);
        this.endDate = SprintEndDate.of(endDate);

        validateDateRange(this.startDate, this.endDate);

        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        this.project = validateProject(project);
    }

    public void updateName(String newName) {
        performUpdate();
        this.name = SprintName.of(newName);
    }

    public void updateGoal(String newGoal) {
        performUpdate();
        this.goal = SprintGoal.of(newGoal);
    }

    public void updateDates(Instant newStartDate, Instant newEndDate) {
        performUpdate();

        SprintStartDate start = SprintStartDate.of(newStartDate);
        SprintEndDate end = SprintEndDate.of(newEndDate);

        validateDateRange(start, end);

        this.startDate = start;
        this.endDate = end;
    }

    public void start() {
        performUpdate();
        if (this.status != SprintStatus.CREATED) {
            throw new ValidationException("Solo se puede iniciar un sprint que ha sido creado");
        }
        this.status = SprintStatus.STARTED;
    }

    public void complete() {
        performUpdate();
        if (this.status != SprintStatus.STARTED) {
            throw new ValidationException("Solo se puede completar un sprint que ha sido iniciado");
        }
        this.status = SprintStatus.COMPLETED;
    }

    public void delete() {
        if (this.isDeleted) {
            throw new ValidationException("El sprint ya se encuentra eliminado");
        }
        this.isDeleted = true;
        this.updatedAt = now();
    }

    public void validateNotDeleted() {
        if (this.isDeleted) {
            throw new ValidationException("No se puede operar sobre un sprint eliminado");
        }
    }

    private void performUpdate() {
        validateNotDeleted();
        this.updatedAt = now();
    }

    private static void validateDateRange(SprintStartDate start, SprintEndDate end) {
        if (!end.value().isAfter(start.value())) {
            throw new ValidationException("La fecha fin del sprint debe ser posterior a la fecha inicio");
        }
    }

    private Project validateProject(Project project) {
        if (project == null) {
            throw new ValidationException("El proyecto no puede estar vacío");
        }
        project.validateActive();
        return project;
    }
}
