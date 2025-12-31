package com.sprintforge.scrum.project.application.service;

import com.sprintforge.common.application.port.result.EmployeeResult;
import com.sprintforge.common.application.port.result.ProjectProgressReportResult;
import com.sprintforge.scrum.common.application.service.support.EmployeeQuerySupport;
import com.sprintforge.scrum.project.application.mapper.ProjectProgressReportMapper;
import com.sprintforge.scrum.project.application.port.in.query.GetProjectProgressReport;
import com.sprintforge.scrum.project.application.port.in.query.GetProjectProgressReportQuery;
import com.sprintforge.scrum.project.application.port.out.persistence.LoadProjectProgressReportRaw;
import com.sprintforge.scrum.project.application.port.out.persistence.raw.ProjectProgressReportRaw;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetProjectProgressReportImpl implements GetProjectProgressReport {

    private final LoadProjectProgressReportRaw loadProjectProgressReportRaw;
    private final EmployeeQuerySupport employeeQuerySupport;

    @Override
    public ProjectProgressReportResult handle(
            GetProjectProgressReportQuery query
    ) {
        ProjectProgressReportRaw raw =
                loadProjectProgressReportRaw.load(query.projectId());

        Set<UUID> employeeIds = raw.projects().stream()
                .flatMap(p -> p.memberEmployeeIds().stream())
                .collect(Collectors.toSet());

        Map<UUID, EmployeeResult> employeeById = employeeQuerySupport.getEmployeeMap(
                employeeIds
        );

        return ProjectProgressReportMapper.toResult(raw, employeeById);
    }
}
