CREATE TABLE board_column
(
    id         UUID PRIMARY KEY,
    sprint_id  UUID        NOT NULL REFERENCES sprint (id),

    name       VARCHAR(60) NOT NULL,
    position   INT         NOT NULL CHECK (position >= 0),
    is_final   BOOLEAN     NOT NULL DEFAULT FALSE,

    is_deleted BOOLEAN     NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX ux_board_column_sprint_name_active
    ON board_column (sprint_id, name) WHERE is_deleted = FALSE;

CREATE UNIQUE INDEX ux_board_column_sprint_position_active
    ON board_column (sprint_id, position) WHERE is_deleted = FALSE;
