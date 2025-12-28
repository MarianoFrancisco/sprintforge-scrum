package com.sprintforge.scrum.workitem.application.mapper;

import com.sprintforge.scrum.board.domain.BoardColumn;
import com.sprintforge.scrum.project.domain.Project;
import com.sprintforge.scrum.sprint.domain.Sprint;
import com.sprintforge.scrum.workitem.application.port.in.command.CreateWorkItemCommand;
import com.sprintforge.scrum.workitem.domain.WorkItem;
import lombok.experimental.UtilityClass;

@UtilityClass
public class WorkItemMapper {
    public WorkItem toDomain(
            CreateWorkItemCommand command,
            int position,
            Project project,
            Sprint sprint,
            BoardColumn boardColumn
    ) {
        if (command == null) {
            return null;
        }

        return new WorkItem(
                position,
                command.title(),
                command.description(),
                command.acceptanceCriteria(),
                command.storyPoints(),
                command.priority(),
                command.developerId(),
                command.productOwnerId(),
                project,
                sprint,
                boardColumn
        );
    }
}
