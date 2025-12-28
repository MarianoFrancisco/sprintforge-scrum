package com.sprintforge.scrum.sprint.infrastructure.adapter.in.rest.controller;

import com.sprintforge.scrum.sprint.domain.Sprint;
import com.sprintforge.scrum.sprint.infrastructure.adapter.in.rest.dto.*;
import com.sprintforge.scrum.sprint.infrastructure.adapter.in.rest.mapper.SprintRestMapper;
import com.sprintforge.scrum.sprint.application.port.in.command.*;
import com.sprintforge.scrum.sprint.application.port.in.query.GetAllSprints;
import com.sprintforge.scrum.sprint.application.port.in.query.GetSprintById;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sprint")
public class SprintController {

    private final GetAllSprints getAllSprints;
    private final GetSprintById getSprintById;
    private final CreateSprint createSprint;
    private final UpdateSprintName updateSprintName;
    private final UpdateSprintGoal updateSprintGoal;
    private final UpdateSprintDates updateSprintDates;
    private final DeleteSprint deleteSprint;


    @GetMapping
    public List<SprintResponseDTO> getAll(
            @RequestParam(required = true) UUID projectId,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) String status
    ) {
        List<Sprint> sprints = getAllSprints.handle(
                SprintRestMapper.toQuery(
                        projectId,
                        searchTerm,
                        status
                )
        );
        return sprints.stream()
                .map(SprintRestMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public SprintResponseDTO getById(@PathVariable("id") UUID id) {
        Sprint sprint = getSprintById.handle(
                SprintRestMapper.toQueryById(
                        id
                )
        );
        return SprintRestMapper.toResponse(sprint);
    }

    @PostMapping
    @ResponseStatus(code = CREATED)
    public SprintResponseDTO create(
            @RequestBody @Valid CreateSprintRequestDTO dto
    ) {
        Sprint sprint = createSprint.handle(
                SprintRestMapper.toCreateCommand(
                        dto
                )
        );
        return SprintRestMapper.toResponse(sprint);
    }

    @PatchMapping("/{id}/name")
    public SprintResponseDTO updateName(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateSprintNameRequestDTO dto
    ) {
        Sprint updated = updateSprintName.handle(
                SprintRestMapper.toUpdateNameCommand(
                        id,
                        dto
                ));
        return SprintRestMapper.toResponse(updated);
    }

    @PatchMapping("/{id}/goal")
    public SprintResponseDTO updateGoal(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateSprintGoalRequestDTO dto
    ) {
        Sprint updated = updateSprintGoal.handle(
                SprintRestMapper.toUpdateGoalCommand(
                        id,
                        dto
                ));
        return SprintRestMapper.toResponse(updated);
    }

    @PatchMapping("/{id}/dates")
    public SprintResponseDTO updateClient(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateSprintDatesRequestDTO dto
    ) {
        Sprint updated = updateSprintDates.handle(
                SprintRestMapper.toUpdateDatesCommand(
                        id,
                        dto
                ));
        return SprintRestMapper.toResponse(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(
            @PathVariable UUID id,
            @RequestBody @Valid DeleteSprintRequestDTO dto
    ) {
        deleteSprint.handle(
                SprintRestMapper.toDeleteCommand(
                        id, dto
                )
        );
    }
}
