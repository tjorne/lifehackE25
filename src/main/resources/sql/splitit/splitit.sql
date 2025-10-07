CREATE TABLE public.groups (
                               group_id SERIAL PRIMARY KEY,
                               name VARCHAR NOT NULL
);

CREATE TABLE public.group_users (
                                    group_id INT NOT NULL,
                                    user_id INT NOT NULL,
                                    PRIMARY KEY (group_id, user_id),
                                    FOREIGN KEY (group_id) REFERENCES public.groups(group_id),
                                    FOREIGN KEY (user_id) REFERENCES public.users(user_id)
);

CREATE TABLE public.expense (
                                expense_id SERIAL PRIMARY KEY,
                                user_id INT,
                                group_id INT,
                                amount DOUBLE PRECISION NOT NULL,
                                description VARCHAR,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY (user_id) REFERENCES public.users(user_id),
                                FOREIGN KEY (group_id) REFERENCES public.groups(group_id)
);
