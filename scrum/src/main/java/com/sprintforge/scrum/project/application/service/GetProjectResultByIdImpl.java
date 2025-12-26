package com.sprintforge.scrum.project.application.service;

import com.sprintforge.scrum.common.application.port.out.rest.employee.query.GetEmployeesByIds;
import com.sprintforge.scrum.common.application.port.out.rest.employee.query.GetEmployeesByIdsQuery;
import com.sprintforge.scrum.common.application.port.result.EmployeeResult;
import com.sprintforge.scrum.project.application.exception.ProjectNotFoundException;
import com.sprintforge.scrum.project.application.mapper.ProjectResultMapper;
import com.sprintforge.scrum.project.application.port.in.query.GetProjectResultById;
import com.sprintforge.scrum.project.application.port.in.query.GetProjectResultByIdQuery;
import com.sprintforge.scrum.project.application.port.out.persistence.FindProjectById;
import com.sprintforge.scrum.project.application.port.result.ProjectResult;
import com.sprintforge.scrum.project.domain.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetProjectResultByIdImpl implements GetProjectResultById {

    private final GetEmployeesByIds getEmployeesByIds;

    private final FindProjectById findProjectById;

    @Override
    public ProjectResult handle(GetProjectResultByIdQuery query) {
        Project project = findProjectById.findById(query.id())
                .orElseThrow(() -> ProjectNotFoundException.byId(query.id()));

        Set<UUID> ids = getEmployeesIds(project);

        List<EmployeeResult> employees = ids.isEmpty()
                ? List.of()
                : getEmployeesByIds.getByIds(new GetEmployeesByIdsQuery(ids));

        return ProjectResultMapper.toResult(project, employees);
    }

    private Set<UUID> getEmployeesIds(Project project) {
        return project.getAssignments().stream()
                .map(a -> a.getEmployeeId().value())
                .collect(toSet());
    }
}
