package com.sprintforge.scrum.sprint.application.service;

import com.sprintforge.common.application.port.result.EmployeeResult;
import com.sprintforge.scrum.common.application.service.support.EmployeeQuerySupport;
import com.sprintforge.scrum.sprint.application.exception.SprintNotFoundException;
import com.sprintforge.scrum.sprint.application.mapper.SprintIntegrationMapper;
import com.sprintforge.scrum.sprint.application.port.in.command.StartSprint;
import com.sprintforge.scrum.sprint.application.port.in.command.StartSprintCommand;
import com.sprintforge.scrum.sprint.application.port.out.event.SprintEventPublisher;
import com.sprintforge.scrum.sprint.application.port.out.persistence.FindSprintById;
import com.sprintforge.scrum.sprint.application.port.out.persistence.SaveSprint;
import com.sprintforge.scrum.sprint.domain.Sprint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class StartSprintImpl implements StartSprint {

    private final FindSprintById findSprintById;
    private final SaveSprint saveSprint;
    private final EmployeeQuerySupport employeeQuerySupport;
    private final SprintEventPublisher sprintEventPublisher;

    @Override
    public Sprint handle(StartSprintCommand command) {
        Sprint sprint = findSprintById.findById(command.id()).orElseThrow(
                () -> SprintNotFoundException.byId(command.id())
        );
        sprint.start();
        Sprint startedSprint = saveSprint.save(sprint);

        EmployeeResult employee =
                employeeQuerySupport.getEmployee(command.employeeId());
        sprintEventPublisher.publishSprintStarted(
                SprintIntegrationMapper.sprintStarted(
                        employee,
                        startedSprint
                )
        );
        return startedSprint;
    }
}
