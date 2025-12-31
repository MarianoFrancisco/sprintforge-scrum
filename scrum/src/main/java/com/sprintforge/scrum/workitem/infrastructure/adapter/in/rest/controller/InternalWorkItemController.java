package com.sprintforge.scrum.workitem.infrastructure.adapter.in.rest.controller;

import com.sprintforge.common.application.port.result.EmployeeProductivityReportResult;
import com.sprintforge.common.infrastructure.adapter.in.rest.dto.EmployeeProductivityReportResponseDTO;
import com.sprintforge.scrum.workitem.application.port.in.query.GetEmployeeProductivityReport;
import com.sprintforge.scrum.workitem.infrastructure.adapter.in.rest.dto.EmployeeProductivityReportRequestDTO;
import com.sprintforge.scrum.workitem.infrastructure.adapter.in.rest.mapper.InternalWorkItemRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal-api/v1/work-item")
public class InternalWorkItemController {

    private final GetEmployeeProductivityReport getEmployeeProductivityReport;

    @GetMapping("/employee-productivity-report")
    public EmployeeProductivityReportResponseDTO getEmployeeProductivityReport(
            @Valid @ModelAttribute EmployeeProductivityReportRequestDTO dto
    ) {
        EmployeeProductivityReportResult result =
                getEmployeeProductivityReport.handle(
                        InternalWorkItemRestMapper.toQuery(dto)
                );

        return InternalWorkItemRestMapper.fromResult(result);
    }
}
