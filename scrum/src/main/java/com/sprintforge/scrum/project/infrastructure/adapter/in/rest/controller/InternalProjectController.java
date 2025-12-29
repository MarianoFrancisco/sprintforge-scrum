package com.sprintforge.scrum.project.infrastructure.adapter.in.rest.controller;

import com.sprintforge.scrum.project.application.port.in.query.GetProjectProgressReport;
import com.sprintforge.scrum.project.application.port.result.ProjectProgressReportResult;
import com.sprintforge.scrum.project.infrastructure.adapter.in.rest.dto.ProjectProgressReportRequestDTO;
import com.sprintforge.scrum.project.infrastructure.adapter.in.rest.dto.ProjectProgressReportResponseDTO;
import com.sprintforge.scrum.project.infrastructure.adapter.in.rest.mapper.InternalProjectRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal-api/v1/project")
public class InternalProjectController {

    private final GetProjectProgressReport getProjectProgressReport;

    @GetMapping("/progress-report")
    public ProjectProgressReportResponseDTO getProgressReport(
            @Valid @ModelAttribute ProjectProgressReportRequestDTO dto
    ) {
        ProjectProgressReportResult result =
                getProjectProgressReport.getProjectProgressReport(
                        InternalProjectRestMapper.toQuery(dto)
                );

        return InternalProjectRestMapper.fromResult(result);
    }
}
