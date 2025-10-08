BEGIN;

CREATE TABLE IF NOT EXISTS public.genre_roulette
(
    genre_id serial NOT NULL,
    movie_id integer NOT NULL,
    genre_name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT genre_roulette_pkey PRIMARY KEY (genre_id)
    );

CREATE TABLE IF NOT EXISTS public.movie_roulette
(
    movie_id serial NOT NULL,
    movie_title character varying COLLATE pg_catalog."default" NOT NULL,
    movie_aired date NOT NULL,
    movie_description character varying COLLATE pg_catalog."default" NOT NULL,
    movie_length integer NOT NULL,
    movie_rating integer NOT NULL,
    CONSTRAINT movie_roulette_pkey PRIMARY KEY (movie_id)
    );

CREATE TABLE IF NOT EXISTS public.provider_roulette
(
    provider_id serial NOT NULL,
    provider_name_id integer NOT NULL,
    movie_id integer NOT NULL,
    provider_url character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT provider_roulette_pkey PRIMARY KEY (provider_id)
    );

CREATE TABLE IF NOT EXISTS public.provider_name_roulette
(
    provider_name_id serial NOT NULL,
    provider_name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT provider_name_roulette_pkey PRIMARY KEY (provider_name_id)Har
    );

CREATE TABLE IF NOT EXISTS public.user_roulette
(
    user_id serial NOT NULL,
    username character varying COLLATE pg_catalog."default" NOT NULL,
    password character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_roulette_pkey PRIMARY KEY (user_id)
    );

CREATE TABLE IF NOT EXISTS public.watchlist_roulette
(
    user_id integer NOT NULL,
    movie_id integer NOT NULL
);

ALTER TABLE IF EXISTS public.genre_roulette
    ADD CONSTRAINT genre_roulette_movie_id_fkey FOREIGN KEY (movie_id)
    REFERENCES public.movie_roulette (movie_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION;

ALTER TABLE IF EXISTS public.provider_roulette
    ADD CONSTRAINT provider_roulette_movie_id_fkey FOREIGN KEY (movie_id)
    REFERENCES public.movie_roulette (movie_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION;

ALTER TABLE IF EXISTS public.provider_roulette
    ADD CONSTRAINT provider_roulette_provider_name_id_fkey FOREIGN KEY (provider_name_id)
    REFERENCES public.provider_name_roulette (provider_name_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION;

ALTER TABLE IF EXISTS public.watchlist_roulette
    ADD CONSTRAINT watchlist_roulette_movie_id_fkey FOREIGN KEY (movie_id)
    REFERENCES public.movie_roulette (movie_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION;

ALTER TABLE IF EXISTS public.watchlist_roulette
    ADD CONSTRAINT watchlist_roulette_user_id_fkey FOREIGN KEY (user_id)
    REFERENCES public.user_roulette (user_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION;

END;