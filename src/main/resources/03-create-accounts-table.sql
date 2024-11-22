CREATE TYPE account_type AS ENUM('savings', 'deposit', 'regular', 'company');
CREATE TYPE account_status AS ENUM('ACTIVE', 'INACTIVE', 'IDLE', 'BLOQUED');

CREATE TABLE accounts (
    id SERIAL PRIMARY KEY,
    account_number VARCHAR(300) NOT NULL,
    account_type account_type DEFAULT 'savings',
    balance FLOAT DEFAULT 0,
    account_status account_status DEFAULT 'ACTIVE',
    fk_client_id integer REFERENCES clients(id) ON DELETE CASCADE
);