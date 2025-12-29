package com.sprintforge.scrum.workitem.application.service;

import com.sprintforge.common.application.port.result.EmployeeResult;
import com.sprintforge.scrum.common.application.port.out.rest.employee.query.GetEmployeesByIds;
import com.sprintforge.scrum.common.application.port.out.rest.employee.query.GetEmployeesByIdsQuery;
import com.sprintforge.scrum.workitem.application.exception.WorkItemNotFoundException;
import com.sprintforge.scrum.workitem.application.mapper.WorkItemResultMapper;
import com.sprintforge.scrum.workitem.application.port.in.query.GetWorkItemResultById;
import com.sprintforge.scrum.workitem.application.port.in.query.GetWorkItemResultByIdQuery;
import com.sprintforge.scrum.workitem.application.port.out.persistence.FindWorkItemById;
import com.sprintforge.scrum.workitem.application.result.WorkItemResult;
import com.sprintforge.scrum.workitem.domain.WorkItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetWorkItemResultByIdImpl implements GetWorkItemResultById {

    private final FindWorkItemById findWorkItemById;
    private final GetEmployeesByIds getEmployeesByIds;

    @Override
    public WorkItemResult handle(GetWorkItemResultByIdQuery query) {
        WorkItem workItem = findWorkItemById.findById(query.id())
                .orElseThrow(() -> WorkItemNotFoundException.byId(query.id()));

        Set<UUID> employeeIds = new HashSet<>();

        if (workItem.getDeveloperId() != null) {
            employeeIds.add(workItem.getDeveloperId().value());
        }
        if (workItem.getProductOwnerId() != null) {
            employeeIds.add(workItem.getProductOwnerId().value());
        }

        List<EmployeeResult> employees = employeeIds.isEmpty()
                ? List.of()
                : getEmployeesByIds.getByIds(new GetEmployeesByIdsQuery(employeeIds));

        return WorkItemResultMapper.toResult(workItem, employees);
    }
}
