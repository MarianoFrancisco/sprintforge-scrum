package com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ProjectEmployeeId implements Serializable {

    @Column(name = "project_id")
    private UUID projectId;

    @Column(name = "employee_id")
    private UUID employeeId;
}
