package com.sprintforge.scrum.workitem.application.service;

import com.sprintforge.scrum.common.application.port.out.rest.employee.command.ValidateEmployees;
import com.sprintforge.scrum.common.application.port.out.rest.employee.command.ValidateEmployeesCommand;
import com.sprintforge.scrum.workitem.application.exception.WorkItemNotFoundException;
import com.sprintforge.scrum.workitem.application.port.in.command.AssignWorkItemDeveloper;
import com.sprintforge.scrum.workitem.application.port.in.command.AssignWorkItemDeveloperCommand;
import com.sprintforge.scrum.workitem.application.port.out.persistence.FindWorkItemById;
import com.sprintforge.scrum.workitem.application.port.out.persistence.SaveWorkItem;
import com.sprintforge.scrum.workitem.domain.WorkItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AssignWorkItemDeveloperImpl implements AssignWorkItemDeveloper {

    private final ValidateEmployees validateEmployees;

    private final FindWorkItemById findWorkItemById;
    private final SaveWorkItem saveWorkItem;

    @Override
    public WorkItem handle(AssignWorkItemDeveloperCommand command) {
        validateEmployees.validate(new ValidateEmployeesCommand(Set.of(command.developerId())));

        WorkItem workItem = findWorkItemById.findById(command.id()).orElseThrow(
                () -> WorkItemNotFoundException.byId(command.id())
        );
        workItem.assignDeveloper(command.developerId());
        WorkItem assignedWorkItem = saveWorkItem.save(workItem);
        return assignedWorkItem;
    }
}
