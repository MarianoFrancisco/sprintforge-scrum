package com.sprintforge.scrum.sprint.application.service;

import com.sprintforge.scrum.board.application.port.in.command.InitializeBoardColumns;
import com.sprintforge.scrum.board.application.port.in.command.InitializeBoardColumnsCommand;
import com.sprintforge.scrum.project.application.port.in.query.GetProjectById;
import com.sprintforge.scrum.project.application.port.in.query.GetProjectByIdQuery;
import com.sprintforge.scrum.project.domain.Project;
import com.sprintforge.scrum.sprint.application.mapper.SprintMapper;
import com.sprintforge.scrum.sprint.application.port.in.command.CreateSprint;
import com.sprintforge.scrum.sprint.application.port.in.command.CreateSprintCommand;
import com.sprintforge.scrum.sprint.application.port.out.persistence.SaveSprint;
import com.sprintforge.scrum.sprint.domain.Sprint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CreateSprintImpl implements CreateSprint {

    private final GetProjectById getProjectById;
    private final SaveSprint saveSprint;
    private final InitializeBoardColumns initializeBoardColumns;

    @Override
    public Sprint handle(CreateSprintCommand command) {
        Project project = getProjectById.handle(
                new GetProjectByIdQuery(command.projectId())
        );

        Sprint sprint = SprintMapper.toDomain(command, project);
        Sprint savedSprint = saveSprint.save(sprint);

        initializeBoardColumns.handle(
                new InitializeBoardColumnsCommand(savedSprint.getId().value())
        );

        return savedSprint;
    }
}
