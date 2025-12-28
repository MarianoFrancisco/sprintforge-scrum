CREATE TABLE project
(
    id              UUID PRIMARY KEY,
    project_key     VARCHAR(10)  NOT NULL UNIQUE,
    name            VARCHAR(50)  NOT NULL UNIQUE,
    description     VARCHAR(255),

    client          VARCHAR(100) NOT NULL,
    area            VARCHAR(80)  NOT NULL,

    budget_amount   NUMERIC(12, 2) CHECK (budget_amount >= 0),
    contract_amount NUMERIC(12, 2) CHECK (contract_amount >= 0),

    is_closed       BOOLEAN      NOT NULL DEFAULT FALSE,
    is_deleted      BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE project_employee
(
    project_id  UUID      NOT NULL REFERENCES project (id),
    employee_id UUID      NOT NULL,
    assigned_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    UNIQUE (project_id, employee_id)
);
