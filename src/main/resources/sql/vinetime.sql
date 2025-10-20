CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) DEFAULT 'USER' NOT NULL
);

CREATE TABLE vt_session (
    session_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    session_type VARCHAR(50) NOT NULL,
    duration_seconds INTEGER NOT NULL,
    completed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);