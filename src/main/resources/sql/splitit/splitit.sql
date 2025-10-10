-- SplitIt App - Test Data Script
-- Assumes users table already exists with users: jon(1), bingo(2), ole(3), dennis(5)
-- Create SplitIt specific tables
-- Create test schema if it doesn't exist
CREATE SCHEMA IF NOT EXISTS test;

CREATE TABLE IF NOT EXISTS public.expense
(
    expense_id serial NOT NULL,
    user_id integer,
    group_id integer,
    amount double precision NOT NULL,
    description character varying COLLATE pg_catalog."default",
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT expense_pkey PRIMARY KEY (expense_id)
    );

CREATE TABLE IF NOT EXISTS public.group_users
(
    group_id integer NOT NULL,
    user_id integer NOT NULL,
    CONSTRAINT group_users_pkey PRIMARY KEY (group_id, user_id)
    );

CREATE TABLE IF NOT EXISTS public.groups
(
    group_id serial NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT groups_pkey PRIMARY KEY (group_id)
    );

-- Add foreign keys
ALTER TABLE IF EXISTS public.expense
DROP CONSTRAINT IF EXISTS expense_group_id_fkey,
    ADD CONSTRAINT expense_group_id_fkey FOREIGN KEY (group_id)
    REFERENCES public.groups (group_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION;

ALTER TABLE IF EXISTS public.expense
DROP CONSTRAINT IF EXISTS expense_user_id_fkey,
    ADD CONSTRAINT expense_user_id_fkey FOREIGN KEY (user_id)
    REFERENCES public.users (user_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION;

ALTER TABLE IF EXISTS public.group_users
DROP CONSTRAINT IF EXISTS group_users_group_id_fkey,
    ADD CONSTRAINT group_users_group_id_fkey FOREIGN KEY (group_id)
    REFERENCES public.groups (group_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION;

ALTER TABLE IF EXISTS public.group_users
DROP CONSTRAINT IF EXISTS group_users_user_id_fkey,
    ADD CONSTRAINT group_users_user_id_fkey FOREIGN KEY (user_id)
    REFERENCES public.users (user_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION;

-- Clear existing SplitIt data
TRUNCATE TABLE public.expense CASCADE;
TRUNCATE TABLE public.group_users CASCADE;
TRUNCATE TABLE public.groups RESTART IDENTITY CASCADE;

-- Insert test groups (using only existing users: 1=jon, 2=bingo, 3=ole, 5=dennis)
INSERT INTO public.groups (name) VALUES
('Sommerhus Tur 2025'),
('Norge Eventyr 2025'),
('Copenhell Festival'),
('London Weekend');

-- Assign users to groups
INSERT INTO public.group_users (group_id, user_id) VALUES
-- Sommerhus Tur: jon, bingo, ole
(1, 1), (1, 2), (1, 3),
-- Norge Eventyr: bingo, dennis
(2, 2), (2, 5),
-- Copenhell: jon, dennis, ole
(3, 1), (3, 5), (3, 3),
-- London: alle fire
(4, 1), (4, 2), (4, 3), (4, 5);

-- Insert expenses
-- Sommerhus Tur expenses
INSERT INTO public.expense (user_id, group_id, amount, description, created_at) VALUES
(1, 1, 847.50, 'Indkøb til weekenden - Føtex', '2025-06-15 10:30:00'),
(2, 1, 650.00, 'Benzin til turen', '2025-06-15 12:00:00'),
(3, 1, 4800.00, 'Leje af sommerhus 3 dage', '2025-06-14 15:00:00'),
(1, 1, 1250.00, 'Aftensmad og vin - Meny', '2025-06-16 19:00:00');

-- Norge Eventyr expenses
INSERT INTO public.expense (user_id, group_id, amount, description, created_at) VALUES
(2, 2, 12000.00, 'Flybilletter SAS København-Oslo', '2025-07-01 09:00:00'),
(5, 2, 3200.00, 'Hotel i Oslo - 2 nætter', '2025-07-05 14:30:00'),
(2, 2, 1680.00, 'Dagligvarer - Rema 1000', '2025-07-08 10:00:00');

-- Copenhell Festival expenses
INSERT INTO public.expense (user_id, group_id, amount, description, created_at) VALUES
(1, 3, 5200.00, 'Copenhell 3-dages billetter (4 stk)', '2025-05-20 12:00:00'),
(5, 3, 520.00, 'Øl og mad dag 1', '2025-06-19 16:00:00'),
(3, 3, 450.00, 'T-shirts og plakater', '2025-06-20 14:30:00');

-- London Weekend expenses
INSERT INTO public.expense (user_id, group_id, amount, description, created_at) VALUES
(3, 4, 3600.00, 'Flybilletter København-London (4 stk)', '2025-09-10 08:00:00'),
(2, 4, 4200.00, 'Airbnb i Camden - 4 nætter', '2025-09-12 14:00:00'),
(1, 4, 720.00, 'Pubbesøg og fish & chips', '2025-09-13 19:00:00');