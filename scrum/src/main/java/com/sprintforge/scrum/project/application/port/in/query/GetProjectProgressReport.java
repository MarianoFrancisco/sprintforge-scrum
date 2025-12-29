package com.sprintforge.scrum.project.application.port.in.query;

import com.sprintforge.common.application.port.result.ProjectProgressReportResult;

public interface GetProjectProgressReport {
    ProjectProgressReportResult getProjectProgressReport(GetProjectProgressReportQuery query);
}
