CREATE TABLE payment
(
    id         UUID PRIMARY KEY,
    project_id UUID           NOT NULL REFERENCES project (id),

    date       DATE           NOT NULL DEFAULT CURRENT_DATE,
    amount     NUMERIC(12, 2) NOT NULL
        CHECK (amount > 0),

    method     VARCHAR(20)    NOT NULL
        CHECK (method IN ('CASH', 'TRANSFER', 'CARD')),

    reference  VARCHAR(60),
    note       VARCHAR(255),

    created_at TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP
);
