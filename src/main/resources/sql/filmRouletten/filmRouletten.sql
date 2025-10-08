
BEGIN;


CREATE TABLE IF NOT EXISTS public.genre
(
    genre_id serial NOT NULL,
    movie_id integer NOT NULL,
    genre_name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT genre_pkey PRIMARY KEY (genre_id)
    );

CREATE TABLE IF NOT EXISTS public.movie
(
    movie_id serial NOT NULL,
    movie_title character varying COLLATE pg_catalog."default" NOT NULL,
    movie_aired date NOT NULL,
    movie_description character varying COLLATE pg_catalog."default" NOT NULL,
    movie_length integer NOT NULL,
    CONSTRAINT movie_pkey PRIMARY KEY (movie_id)
    );

CREATE TABLE IF NOT EXISTS public.provider
(
    provider_id serial NOT NULL,
    provider_name_id integer NOT NULL,
    movie_id integer NOT NULL,
    provider_url character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT provider_pkey PRIMARY KEY (provider_id)
    );

CREATE TABLE IF NOT EXISTS public.provider_name
(
    provider_name_id serial NOT NULL,
    provider_name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT provider_name_pkey PRIMARY KEY (provider_name_id)
    );

CREATE TABLE IF NOT EXISTS public."user"
(
    user_id serial NOT NULL,
    username character varying COLLATE pg_catalog."default" NOT NULL,
    password character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (user_id)
    );

CREATE TABLE IF NOT EXISTS public.watchlist
(
    user_id integer NOT NULL,
    movie_id integer NOT NULL
);

ALTER TABLE IF EXISTS public.genre
    ADD CONSTRAINT genre_movie_id_fkey FOREIGN KEY (movie_id)
    REFERENCES public.movie (movie_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.provider
    ADD CONSTRAINT provider_movie_id_fkey FOREIGN KEY (movie_id)
    REFERENCES public.movie (movie_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.provider
    ADD CONSTRAINT provider_provider_name_id_fkey FOREIGN KEY (provider_name_id)
    REFERENCES public.provider_name (provider_name_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.watchlist
    ADD CONSTRAINT movie_id FOREIGN KEY (movie_id)
    REFERENCES public.movie (movie_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.watchlist
    ADD CONSTRAINT user_id FOREIGN KEY (user_id)
    REFERENCES public."user" (user_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION;

END;