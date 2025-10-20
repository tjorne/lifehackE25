CREATE TABLE vt_session (
    session_id SEIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    session_type VARCHAR (50) NOT NULL,
    duration_minutes INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL
);