package com.sprintforge.scrum.project.application.port.out.persistence;

import com.sprintforge.scrum.project.application.port.out.persistence.raw.ProjectProgressReportRaw;

import java.util.UUID;

public interface LoadProjectProgressReportRaw {
    ProjectProgressReportRaw load(UUID projectId);
}
