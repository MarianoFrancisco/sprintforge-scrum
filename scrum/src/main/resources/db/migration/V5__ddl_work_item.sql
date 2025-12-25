CREATE TABLE work_item
(
    id                  UUID PRIMARY KEY,
    project_id          UUID          NOT NULL REFERENCES project (id),
    sprint_id           UUID NULL REFERENCES sprint(id),
    column_id           UUID NULL REFERENCES board_column(id),
    position            INT           NOT NULL CHECK (position >= 0),

    title               VARCHAR(200)  NOT NULL,
    description         VARCHAR(2000) NOT NULL,
    acceptance_criteria VARCHAR(2000) NOT NULL,

    story_points        INT NULL CHECK (story_points IS NULL OR story_points >= 0),
    priority            INT           NOT NULL CHECK (priority BETWEEN 1 AND 5),

    developer_id        UUID NULL,
    product_owner_id    UUID NULL,

    is_deleted          BOOLEAN       NOT NULL DEFAULT FALSE,
    created_at          TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_backlog_has_no_column CHECK (
        (sprint_id IS NULL AND column_id IS NULL) OR
        (sprint_id IS NOT NULL)
        )
);
