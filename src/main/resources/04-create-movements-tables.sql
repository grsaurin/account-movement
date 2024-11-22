CREATE TYPE movement_types AS ENUM('rent', 'transfer', 'payment', 'others');

CREATE TABLE movements (
    id SERIAL PRIMARY KEY,
    account_id integer REFERENCES accounts(id),
    mov_type movement_types DEFAULT 'others',
    amount FLOAT NOT NULL,
    balance FLOAT NOT NULL,
    date TIMESTAMP DEFAULT (CURRENT_DATE)
);