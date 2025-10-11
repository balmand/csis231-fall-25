-- USERS TABLE (for login)
CREATE TABLE IF NOT EXISTS users (
                                     id BIGSERIAL PRIMARY KEY,
                                     username VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,  -- store BCrypt hash
    role VARCHAR(50) DEFAULT 'USER', -- USER or ADMIN
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- TOKENS TABLE (store refresh tokens for JWT)
CREATE TABLE IF NOT EXISTS user_tokens (
                                           id BIGSERIAL PRIMARY KEY,
                                           user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    refresh_token TEXT NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    revoked BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- BOOK TABLE
CREATE TABLE IF NOT EXISTS book (
                                    id BIGSERIAL PRIMARY KEY,
                                    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    "year" INTEGER,
    price DOUBLE PRECISION,
    isbn VARCHAR(255)
    );

-- CUSTOMER TABLE
CREATE TABLE IF NOT EXISTS customer (
                                        id BIGSERIAL PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE
    );
