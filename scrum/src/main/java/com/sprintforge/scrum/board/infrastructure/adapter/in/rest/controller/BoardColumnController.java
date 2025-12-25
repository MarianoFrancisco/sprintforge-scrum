package com.sprintforge.scrum.board.infrastructure.adapter.in.rest.controller;

import com.sprintforge.scrum.board.application.port.in.command.CreateBoardColumn;
import com.sprintforge.scrum.board.application.port.in.command.DeleteBoardColumn;
import com.sprintforge.scrum.board.application.port.in.command.UpdateBoardColumnName;
import com.sprintforge.scrum.board.application.port.in.command.MoveBoardColumn;
import com.sprintforge.scrum.board.application.port.in.query.GetAllBoardColumnsBySprintId;
import com.sprintforge.scrum.board.application.port.in.query.GetBoardColumnById;
import com.sprintforge.scrum.board.domain.BoardColumn;
import com.sprintforge.scrum.board.infrastructure.adapter.in.rest.dto.*;
import com.sprintforge.scrum.board.infrastructure.adapter.in.rest.mapper.BoardColumnRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board-column")
public class BoardColumnController {

    private final GetAllBoardColumnsBySprintId getAllBoardColumnsBySprintId;
    private final GetBoardColumnById getBoardColumnById;
    private final CreateBoardColumn createBoardColumn;
    private final UpdateBoardColumnName updateBoardColumnName;
    private final MoveBoardColumn moveBoardColumn;
    private final DeleteBoardColumn deleteBoardColumn;

    @GetMapping
    public List<BoardColumnResponseDTO> getAll(
            @RequestParam UUID projectId,
            @RequestParam(required = false) String searchTerm
    ) {
        List<BoardColumn> boardColumns = getAllBoardColumnsBySprintId.handle(
                BoardColumnRestMapper.toQuery(
                        projectId,
                        searchTerm
                )
        );
        return boardColumns.stream()
                .map(BoardColumnRestMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public BoardColumnResponseDTO getById(
            @PathVariable UUID id
    ) {
        BoardColumn boardColumn = getBoardColumnById.handle(
                BoardColumnRestMapper.toQueryById(
                        id
                )
        );
        return BoardColumnRestMapper.toResponse(boardColumn);
    }

    @PostMapping
    @ResponseStatus(code = CREATED)
    public BoardColumnResponseDTO create(
            @RequestBody @Valid CreateBoardColumnRequestDTO dto
    ) {
        BoardColumn boardColumn = createBoardColumn.handle(
                BoardColumnRestMapper.toCreateCommand(
                        dto
                )
        );
        return BoardColumnRestMapper.toResponse(boardColumn);
    }


    @PatchMapping("/{id}/name")
    public BoardColumnResponseDTO updateName(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateBoardColumnNameRequestDTO dto
    ) {
        BoardColumn updated = updateBoardColumnName.handle(
                BoardColumnRestMapper.toUpdateNameCommand(
                        id,
                        dto
                ));
        return BoardColumnRestMapper.toResponse(updated);
    }

    @PatchMapping("/position")
    public BoardColumnResponseDTO updatePosition(
            @RequestBody @Valid MoveBoardColumnRequestDTO dto
    ) {
        BoardColumn updated = moveBoardColumn.handle(
                BoardColumnRestMapper.toMoveBoarColumnCommand(
                        dto
                ));
        return BoardColumnRestMapper.toResponse(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(
            @PathVariable UUID id,
            @RequestBody @Valid DeleteBoardColumnRequestDTO dto
    ) {
        deleteBoardColumn.handle(
                BoardColumnRestMapper.toDeleteCommand(
                        id,
                        dto
                )
        );
    }

}
