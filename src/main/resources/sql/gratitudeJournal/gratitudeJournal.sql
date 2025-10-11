BEGIN;


CREATE TABLE IF NOT EXISTS public.journal_item
(
    item_id bigserial NOT NULL,
    log_id bigint NOT NULL,
    item_type text COLLATE pg_catalog."default" NOT NULL,
    "position" smallint,
    content text COLLATE pg_catalog."default",
    CONSTRAINT journal_item_pkey PRIMARY KEY (item_id)
    );

CREATE TABLE IF NOT EXISTS public.journal_log
(
    log_id bigserial NOT NULL,
    log_date date NOT NULL,
    CONSTRAINT journal_log_pkey PRIMARY KEY (log_id)
    );

CREATE TABLE IF NOT EXISTS public.users
(
    user_id serial NOT NULL,
    username character varying(50) COLLATE pg_catalog."default" NOT NULL,
    password character varying(50) COLLATE pg_catalog."default" NOT NULL,
    role character varying(20) COLLATE pg_catalog."default" NOT NULL DEFAULT USER,
    CONSTRAINT users_pkey PRIMARY KEY (user_id),
    CONSTRAINT unique_username UNIQUE (username)
    );

ALTER TABLE IF EXISTS public.journal_item
    ADD CONSTRAINT journal_item_log_id_fkey FOREIGN KEY (log_id)
    REFERENCES public.journal_log (log_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE CASCADE;

END;