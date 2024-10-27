CREATE TABLE sellers (
    id CHAR(32) NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    created_at TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE charges (
    id CHAR(32) NOT NULL PRIMARY KEY,
    seller_id CHAR(32) REFERENCES sellers(id) ON DELETE CASCADE,
    total NUMERIC(10, 2) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE payments (
    id CHAR(32) NOT NULL PRIMARY KEY,
    charge_id CHAR(32) REFERENCES charges(id) ON DELETE CASCADE,
    seller_id CHAR(32) REFERENCES sellers(id) ON DELETE CASCADE,
    status VARCHAR(100),
    total NUMERIC(10, 2) NOT NULL,
    created_at TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO sellers (id, name, email) VALUES
    ('123e4567e89b12d3a456426614174000', 'Alice Johnson', 'alice@example.com'),
    ('123e4567e89b12d3a456426614174001', 'Bob Smith', 'bob@example.com'),
    ('123e4567e89b12d3a456426614174002', 'Charlie Davis', 'charlie@example.com');

INSERT INTO charges (id, seller_id, total, description) VALUES
    ('223e4567e89b12d3a456426614174003', '123e4567e89b12d3a456426614174000', 100.00, 'Charge for product A'),
    ('223e4567e89b12d3a456426614174004', '123e4567e89b12d3a456426614174000', 150.00, 'Charge for product B'),
    ('223e4567e89b12d3a456426614174005', '123e4567e89b12d3a456426614174001', 200.00, 'Charge for consulting services'),
    ('223e4567e89b12d3a456426614174006', '123e4567e89b12d3a456426614174001', 50.00, 'Charge for support services'),
    ('223e4567e89b12d3a456426614174007', '123e4567e89b12d3a456426614174002', 75.00, 'Charge for digital product');
