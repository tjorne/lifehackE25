CREATE TABLE vt_session (
    session_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    session_type VARCHAR (50) NOT NULL,
    created_at TIMESTAMP NOT NULL CURRENT_TIMESTAMP,
    ended_at TIMESTAMP
);