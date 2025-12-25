package com.sprintforge.scrum.workitem.application.service;

import com.sprintforge.scrum.board.application.port.in.query.GetBoardColumnById;
import com.sprintforge.scrum.board.application.port.in.query.GetBoardColumnByIdQuery;
import com.sprintforge.scrum.board.application.port.in.query.GetDefaultBoardColumnBySprint;
import com.sprintforge.scrum.board.application.port.in.query.GetDefaultBoardColumnBySprintQuery;
import com.sprintforge.scrum.board.domain.BoardColumn;
import com.sprintforge.scrum.common.application.port.out.rest.employee.command.ValidateEmployees;
import com.sprintforge.scrum.common.application.port.out.rest.employee.command.ValidateEmployeesCommand;
import com.sprintforge.scrum.project.application.port.in.query.GetProjectById;
import com.sprintforge.scrum.project.application.port.in.query.GetProjectByIdQuery;
import com.sprintforge.scrum.project.domain.Project;
import com.sprintforge.scrum.sprint.application.port.in.query.GetSprintById;
import com.sprintforge.scrum.sprint.application.port.in.query.GetSprintByIdQuery;
import com.sprintforge.scrum.sprint.domain.Sprint;
import com.sprintforge.scrum.workitem.application.mapper.WorkItemMapper;
import com.sprintforge.scrum.workitem.application.port.in.command.CreateWorkItem;
import com.sprintforge.scrum.workitem.application.port.in.command.CreateWorkItemCommand;
import com.sprintforge.scrum.workitem.application.port.out.persistence.FindNextBacklogPosition;
import com.sprintforge.scrum.workitem.application.port.out.persistence.FindNextBoardColumnPosition;
import com.sprintforge.scrum.workitem.application.port.out.persistence.SaveWorkItem;
import com.sprintforge.scrum.workitem.domain.WorkItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CreateWorkItemImpl implements CreateWorkItem {

    private final ValidateEmployees validateEmployees;

    private final GetProjectById getProjectById;
    private final GetSprintById getSprintById;
    private final GetBoardColumnById getBoardColumnById;
    private final GetDefaultBoardColumnBySprint getDefaultBoardColumnBySprint;

    private final FindNextBoardColumnPosition findNextBoardColumnPosition;
    private final FindNextBacklogPosition findNextBacklogPosition;
    private final SaveWorkItem saveWorkItem;

    @Override
    public WorkItem handle(CreateWorkItemCommand command) {
        this.validateAssignments(command);

        Project project = loadProject(command);
        Sprint sprint = loadSprint(command);
        BoardColumn boardColumn = resolveBoardColumn(command, sprint);

        int position = determinePosition(project, sprint, boardColumn);

        WorkItem workItem = WorkItemMapper.toDomain(
                command,
                position,
                project,
                sprint,
                boardColumn
        );

        return saveWorkItem.save(workItem);
    }

    private Project loadProject(CreateWorkItemCommand command) {
        return getProjectById.handle(new GetProjectByIdQuery(command.projectId()));
    }

    private Sprint loadSprint(CreateWorkItemCommand command) {
        if (command.sprintId() == null) return null;
        return getSprintById.handle(new GetSprintByIdQuery(command.sprintId()));
    }

    private BoardColumn resolveBoardColumn(CreateWorkItemCommand command, Sprint sprint) {
        if (sprint == null) return null;

        if (command.boardColumnId() != null) {
            return getBoardColumnById.handle(new GetBoardColumnByIdQuery(command.boardColumnId()));
        }

        return getDefaultBoardColumnBySprint.handle(
                new GetDefaultBoardColumnBySprintQuery(sprint.getId().value())
        );
    }

    private int determinePosition(Project project, Sprint sprint, BoardColumn boardColumn) {
        if (sprint == null) {
            return findNextBacklogPosition.findNextPosition(project.getId().value());
        }
        return findNextBoardColumnPosition.findNextPosition(
                project.getId().value(),
                sprint.getId().value(),
                boardColumn.getId().value()
        );
    }

    private void validateAssignments(CreateWorkItemCommand command) {
        Set<UUID> employeeIds = Stream.of(command.developerId(), command.productOwnerId())
                .filter(Objects::nonNull)
                .collect(toSet());

        if (!employeeIds.isEmpty()) {
            validateEmployees.validate(new ValidateEmployeesCommand(employeeIds));
        }
    }
}
