CREATE TABLE groups (
                        group_id SERIAL PRIMARY KEY,
                        name VARCHAR NOT NULL,
);

CREATE TABLE group_users (
                             group_id INT NOT NULL,
                             user_id INT NOT NULL,
                             PRIMARY KEY (group_id, user_id),
                             FOREIGN KEY (group_id) REFERENCES groups(group_id),
                             FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE expense (
                         expense_id SERIAL PRIMARY KEY,
                         user_id INT,
                         group_id INT,
                         amount DOUBLE PRECISION NOT NULL,
                         description VARCHAR,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (user_id) REFERENCES users(user_id),
                         FOREIGN KEY (group_id) REFERENCES groups(group_id)
);