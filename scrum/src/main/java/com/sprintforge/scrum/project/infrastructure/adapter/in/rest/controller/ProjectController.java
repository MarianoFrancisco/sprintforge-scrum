package com.sprintforge.scrum.project.infrastructure.adapter.in.rest.controller;

import com.sprintforge.scrum.project.application.port.in.command.*;
import com.sprintforge.scrum.project.application.port.in.query.GetAllProjects;
import com.sprintforge.scrum.project.application.port.in.query.GetProjectById;
import com.sprintforge.scrum.project.domain.Project;
import com.sprintforge.scrum.project.infrastructure.adapter.in.rest.dto.*;
import com.sprintforge.scrum.project.infrastructure.adapter.in.rest.mapper.ProjectRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final GetAllProjects getAllProjects;
    private final GetProjectById getProjectById;
    private final CreateProject createProject;
    private final UpdateProjectName updateProjectName;
    private final UpdateProjectDescription updateProjectDescription;
    private final UpdateProjectClient updateProjectClient;
    private final UpdateProjectArea updateProjectArea;
    private final UpdateProjectAmounts updateProjectAmounts;
    private final AssignProjectEmployees assignProjectEmployees;
    private final UnassignProjectEmployees unassignProjectEmployees;
    private final OpenProject openProject;
    private final CloseProject closeProjects;
    private final DeleteProject deleteProject;


    @GetMapping
    public List<ProjectResponseDTO> getAll(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) Boolean isActive
    ) {
        List<Project> projects = getAllProjects.handle(
                ProjectRestMapper.toQuery(
                        searchTerm,
                        isActive
                )
        );
        return projects.stream()
                .map(ProjectRestMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ProjectResponseDTO getById(@PathVariable("id") UUID id) {
        Project project = getProjectById.handle(
                ProjectRestMapper.toQuery(
                        id
                )
        );
        return ProjectRestMapper.toResponse(project);
    }

    @PostMapping
    @ResponseStatus(code = CREATED)
    public ProjectResponseDTO create(
            @RequestBody @Valid CreateProjectRequestDTO dto
    ) {
        Project project = createProject.handle(
                ProjectRestMapper.toCreateCommand(
                        dto
                )
        );
        return ProjectRestMapper.toResponse(project);
    }

    @PatchMapping("/{id}/name")
    public ProjectResponseDTO updateName(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateProjectNameRequestDTO dto
    ) {
        Project updated = updateProjectName.handle(
                ProjectRestMapper.toUpdateNameCommand(
                        id,
                        dto
                ));
        return ProjectRestMapper.toResponse(updated);
    }

    @PatchMapping("/{id}/description")
    public ProjectResponseDTO updateDescription(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateProjectDescriptionRequestDTO dto
    ) {
        Project updated = updateProjectDescription.handle(
                ProjectRestMapper.toUpdateDescriptionCommand(
                        id,
                        dto
                ));
        return ProjectRestMapper.toResponse(updated);
    }

    @PatchMapping("/{id}/client")
    public ProjectResponseDTO updateClient(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateProjectClientRequestDTO dto
    ) {
        Project updated = updateProjectClient.handle(
                ProjectRestMapper.toUpdateClientCommand(
                        id,
                        dto
                ));
        return ProjectRestMapper.toResponse(updated);
    }

    @PatchMapping("/{id}/area")
    public ProjectResponseDTO updateArea(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateProjectAreaRequestDTO dto
    ) {
        Project updated = updateProjectArea.handle(
                ProjectRestMapper.toUpdateAreaCommand(
                        id,
                        dto
                ));
        return ProjectRestMapper.toResponse(updated);
    }

    @PatchMapping("/{id}/amounts")
    public ProjectResponseDTO updateAmounts(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateProjectAmountsRequestDTO dto
    ) {
        Project updated = updateProjectAmounts.handle(
                ProjectRestMapper.toUpdateAmountsCommand(
                        id,
                        dto
                ));
        return ProjectRestMapper.toResponse(updated);
    }

    @PatchMapping("/{id}/assign")
    public ProjectResponseDTO assign(
            @PathVariable UUID id,
            @RequestBody @Valid AssignProjectEmployeesRequestDTO dto
    ) {
        Project updated = assignProjectEmployees.handle(
                ProjectRestMapper.toAssignEmployeesCommand(
                        id,
                        dto
                ));
        return ProjectRestMapper.toResponse(updated);
    }

    @PatchMapping("/{id}/unassign")
    public ProjectResponseDTO unassign(
            @PathVariable UUID id,
            @RequestBody @Valid UnassignProjectEmployeesRequestDTO dto
    ) {
        Project updated = unassignProjectEmployees.handle(
                ProjectRestMapper.toUnassignEmployeesCommand(
                        id,
                        dto
                ));
        return ProjectRestMapper.toResponse(updated);
    }

    @PatchMapping("/{id}/open")
    public ProjectResponseDTO open(
            @PathVariable UUID id,
            @RequestBody @Valid OpenProjectRequestDTO dto
    ) {
        Project open = openProject.handle(
                ProjectRestMapper.toOpenCommand(
                        id,
                        dto
                )
        );
        return ProjectRestMapper.toResponse(open);
    }

    @PatchMapping("/{id}/close")
    public ProjectResponseDTO close(
            @PathVariable UUID id,
            @RequestBody @Valid CloseProjectRequestDTO dto
    ) {
        Project closed = closeProjects.handle(
                ProjectRestMapper.toCloseCommand(
                        id,
                        dto
                )
        );
        return ProjectRestMapper.toResponse(closed);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(
            @PathVariable UUID id,
            @RequestBody @Valid DeleteProjectRequestDTO dto
    ) {
        deleteProject.handle(
                ProjectRestMapper.toDeleteCommand(
                        id,
                        dto
                )
        );
    }
}
