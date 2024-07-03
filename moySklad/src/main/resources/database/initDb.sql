
DROP TABLE IF EXISTS products;

CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(4096),
    price DOUBLE PRECISION CHECK (price >= 0) DEFAULT 0,
    available BOOLEAN DEFAULT FALSE
);
