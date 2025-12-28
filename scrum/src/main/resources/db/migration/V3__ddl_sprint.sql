CREATE TABLE sprint
(
    id         UUID PRIMARY KEY,
    project_id UUID         NOT NULL,
    name       VARCHAR(120) NOT NULL,
    goal       VARCHAR(500),
    status     VARCHAR(20)  NOT NULL CHECK (status IN ('CREATED', 'STARTED', 'COMPLETED')),
    start_date TIMESTAMP(0) NOT NULL,
    end_date   TIMESTAMP(0) NOT NULL,
    is_deleted BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_sprint_project
        FOREIGN KEY (project_id) REFERENCES project (id)
            ON DELETE RESTRICT
);
