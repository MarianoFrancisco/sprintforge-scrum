package com.sprintforge.scrum.project.application.port.in.query;

import com.sprintforge.scrum.project.application.port.result.ProjectProgressReportResult;

public interface GetProjectProgressReport {
    ProjectProgressReportResult getProjectProgressReport(GetProjectProgressReportQuery query);
}
