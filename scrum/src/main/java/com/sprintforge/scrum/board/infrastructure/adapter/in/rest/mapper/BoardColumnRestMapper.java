package com.sprintforge.scrum.board.infrastructure.adapter.in.rest.mapper;

import com.sprintforge.scrum.board.application.port.in.query.GetAllBoardColumnsBySprintIdQuery;
import com.sprintforge.scrum.board.application.port.in.command.*;
import com.sprintforge.scrum.board.application.port.in.query.GetBoardColumnByIdQuery;
import com.sprintforge.scrum.board.domain.BoardColumn;
import com.sprintforge.scrum.board.infrastructure.adapter.in.rest.dto.*;
import com.sprintforge.scrum.sprint.infrastructure.adapter.in.rest.mapper.SprintRestMapper;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class BoardColumnRestMapper {
    public GetAllBoardColumnsBySprintIdQuery toQuery(
            UUID projectId,
            String searchTerm
    ) {
        return new GetAllBoardColumnsBySprintIdQuery(projectId, searchTerm);
    }

    public GetBoardColumnByIdQuery toQueryById(UUID id) {
        return new GetBoardColumnByIdQuery(id);
    }

    public CreateBoardColumnCommand toCreateCommand(CreateBoardColumnRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return new CreateBoardColumnCommand(
                dto.employeeId(),
                dto.sprintId(),
                dto.name()
        );
    }

    public BoardColumnResponseDTO toResponse(BoardColumn boardColumn) {
        if (boardColumn == null) {
            return null;
        }
        return new BoardColumnResponseDTO(
                boardColumn.getId().value(),
                boardColumn.getName().value(),
                boardColumn.getPosition().value(),
                boardColumn.isFinal(),
                boardColumn.isDeleted(),
                boardColumn.getCreatedAt(),
                boardColumn.getUpdatedAt(),
                SprintRestMapper.toResponse(boardColumn.getSprint())
        );
    }

    public UpdateBoardColumnNameCommand toUpdateNameCommand(
            UUID id,
            UpdateBoardColumnNameRequestDTO dto
    ) {
        if (dto == null) {
            return null;
        }
        return new UpdateBoardColumnNameCommand(
                dto.employeeId(),
                id,
                dto.name()
        );
    }

    public MoveBoardColumnCommand toMoveBoarColumnCommand(
            MoveBoardColumnRequestDTO dto
    ) {
        if (dto == null) {
            return null;
        }

        return new MoveBoardColumnCommand(
                dto.employeeId(),
                dto.sprintId(),
                dto.newPosition()
        );
    }

    public DeleteBoardColumnCommand toDeleteCommand(
            UUID id,
            DeleteBoardColumnRequestDTO dto
    ) {
        return new DeleteBoardColumnCommand(
                dto.employeeId(),
                id,
                dto.targetBoardColumnId()
        );
    }
}
