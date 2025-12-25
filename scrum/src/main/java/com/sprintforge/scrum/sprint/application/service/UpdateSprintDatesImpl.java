package com.sprintforge.scrum.sprint.application.service;

import com.sprintforge.scrum.sprint.application.exception.SprintNotFoundException;
import com.sprintforge.scrum.sprint.application.port.in.command.UpdateSprintDates;
import com.sprintforge.scrum.sprint.application.port.in.command.UpdateSprintDatesCommand;
import com.sprintforge.scrum.sprint.application.port.out.persistence.FindSprintById;
import com.sprintforge.scrum.sprint.application.port.out.persistence.SaveSprint;
import com.sprintforge.scrum.sprint.domain.Sprint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UpdateSprintDatesImpl implements UpdateSprintDates {

    private final FindSprintById findSprintById;
    private final SaveSprint saveSprint;

    @Override
    public Sprint handle(UpdateSprintDatesCommand command) {
        Sprint sprint = findSprintById.findById(command.id()).orElseThrow(
                () -> SprintNotFoundException.byId(command.id())
        );
        sprint.updateDates(command.startDate(), command.endDate());
        Sprint updatedSprint = saveSprint.save(sprint);
        return updatedSprint;
    }
}
