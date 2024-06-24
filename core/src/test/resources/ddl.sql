CREATE SCHEMA IF NOT EXISTS schema;

CREATE TABLE IF NOT EXISTS equations
(
    id SERIAL PRIMARY KEY,
    left_part VARCHAR(255) NOT NULL,
    right_part VARCHAR(255) NOT NULL,
    equation VARCHAR(255) UNIQUE NOT NULL
    );

CREATE TABLE IF NOT EXISTS roots
(
    id SERIAL PRIMARY KEY,
    equation_id INT REFERENCES equations(id) ON DELETE CASCADE NOT NULL,
    root_value REAL NOT NULL
);