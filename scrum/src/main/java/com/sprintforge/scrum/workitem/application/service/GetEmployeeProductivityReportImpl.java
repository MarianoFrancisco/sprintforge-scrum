package com.sprintforge.scrum.workitem.application.service;

import com.sprintforge.common.application.port.result.EmployeeProductivityReportResult;
import com.sprintforge.common.application.port.result.EmployeeResult;
import com.sprintforge.scrum.common.application.service.support.EmployeeQuerySupport;
import com.sprintforge.scrum.workitem.application.mapper.EmployeeProductivityReportMapper;
import com.sprintforge.scrum.workitem.application.port.in.query.GetEmployeeProductivityReport;
import com.sprintforge.scrum.workitem.application.port.in.query.GetEmployeeProductivityReportQuery;
import com.sprintforge.scrum.workitem.application.port.out.persistence.LoadEmployeeProductivityReportRaw;
import com.sprintforge.scrum.workitem.application.port.out.persistence.raw.EmployeeProductivityRawItem;
import com.sprintforge.scrum.workitem.application.port.out.persistence.raw.EmployeeProductivityReportRaw;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetEmployeeProductivityReportImpl implements GetEmployeeProductivityReport {

    private final LoadEmployeeProductivityReportRaw loadEmployeeProductivityReportRaw;
    private final EmployeeQuerySupport employeeQuerySupport;

    @Override
    public EmployeeProductivityReportResult handle(GetEmployeeProductivityReportQuery query) {
        EmployeeProductivityReportRaw raw = loadEmployeeProductivityReportRaw.load(query);

        Set<UUID> employeeIds = raw.employees()
                .stream()
                .map(EmployeeProductivityRawItem::employeeId)
                .collect(Collectors.toSet());

        Map<UUID, EmployeeResult> employeeById = employeeQuerySupport.getEmployeeMap(
                employeeIds
        );

        return EmployeeProductivityReportMapper.toResult(raw, employeeById);
    }
}
