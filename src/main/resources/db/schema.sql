-- Create tasks table
CREATE TABLE IF NOT EXISTS tasks (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    priority TEXT NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);