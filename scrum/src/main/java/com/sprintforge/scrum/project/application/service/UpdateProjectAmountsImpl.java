package com.sprintforge.scrum.project.application.service;

import com.sprintforge.scrum.project.application.exception.ProjectNotFoundException;
import com.sprintforge.scrum.project.application.port.in.command.UpdateProjectAmounts;
import com.sprintforge.scrum.project.application.port.in.command.UpdateProjectAmountsCommand;
import com.sprintforge.scrum.project.application.port.out.persistence.FindProjectById;
import com.sprintforge.scrum.project.application.port.out.persistence.SaveProject;
import com.sprintforge.scrum.project.domain.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UpdateProjectAmountsImpl implements UpdateProjectAmounts {

    private final FindProjectById findProjectById;
    private final SaveProject saveProject;

    @Override
    public Project handle(UpdateProjectAmountsCommand command) {
        Project project = findProjectById.findById(command.id()).orElseThrow(
                () -> ProjectNotFoundException.byId(command.id())
        );
        project.updateAmounts(command.budgetAmount(), command.contractAmount());
        Project activatedProject = saveProject.save(project);
        return activatedProject;
    }
}
