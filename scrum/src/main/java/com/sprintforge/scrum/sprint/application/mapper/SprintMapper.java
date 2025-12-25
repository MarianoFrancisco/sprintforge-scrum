package com.sprintforge.scrum.sprint.application.mapper;

import com.sprintforge.scrum.project.domain.Project;
import com.sprintforge.scrum.sprint.application.port.in.command.CreateSprintCommand;
import com.sprintforge.scrum.sprint.domain.Sprint;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SprintMapper {
    public Sprint toDomain(CreateSprintCommand command, Project project) {
        if (command == null) {
            return null;
        }
        return new Sprint(
                command.name(),
                command.goal(),
                command.startDate(),
                command.endDate(),
                project
        );
    }
}
