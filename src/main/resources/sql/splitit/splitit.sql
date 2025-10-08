-- RESET KOMPLET DATABASE
TRUNCATE TABLE public.expense CASCADE;
TRUNCATE TABLE public.group_users CASCADE;
TRUNCATE TABLE public.groups RESTART IDENTITY CASCADE;
TRUNCATE TABLE public.users RESTART IDENTITY CASCADE;

INSERT INTO public.users (username, password, role) VALUES
                                                        ('jon', '1234', 'user'),
                                                        ('bingo', '1234', 'admin'),
                                                        ('ole', '1234', 'user'),
                                                        ('dennis', '1234', 'user'),
                                                        ('oste', 'haps', 'user'),
                                                        ('anna', '1234', 'user'),
                                                        ('peter', '1234', 'user'),
                                                        ('sofie', '1234', 'user'),
                                                        ('morten', '1234', 'user'),
                                                        ('laura', '1234', 'user'),
                                                        ('kasper', '1234', 'user'),
                                                        ('maria', '1234', 'user'),
                                                        ('thomas', '1234', 'user'),
                                                        ('emma', '1234', 'user');


INSERT INTO public.groups (name) VALUES
                                     ('Sommerhus Tur 2025'),
                                     ('Norge Eventyr 2025'),
                                     ('Copenhell Festival'),
                                     ('Sommer i Sunny Beach'),
                                     ('London baby'),
                                     ('Oste weekend i Sydfrankrig');


INSERT INTO public.group_users (group_id, user_id) VALUES
                                                       (1, 1),  -- jon
                                                       (1, 2),  -- bingo
                                                       (1, 3),  -- ole
                                                       (1, 5);  -- oste


INSERT INTO public.group_users (group_id, user_id) VALUES
                                                       (2, 4),  -- dennis
                                                       (2, 6),  -- anna
                                                       (2, 7),  -- peter
                                                       (2, 8);  -- sofie


INSERT INTO public.group_users (group_id, user_id) VALUES
                                                       (3, 1),  -- jon
                                                       (3, 4),  -- dennis
                                                       (3, 9),  -- morten
                                                       (3, 10); -- laura


INSERT INTO public.group_users (group_id, user_id) VALUES
                                                       (4, 2),  -- bingo
                                                       (4, 6),  -- anna
                                                       (4, 11), -- kasper
                                                       (4, 12), -- maria
                                                       (4, 13); -- thomas


INSERT INTO public.group_users (group_id, user_id) VALUES
                                                       (5, 3),  -- ole
                                                       (5, 7),  -- peter
                                                       (5, 14); -- emma


INSERT INTO public.group_users (group_id, user_id) VALUES
                                                       (6, 5),  -- oste
                                                       (6, 1),  -- jon
                                                       (6, 6),  -- anna
                                                       (6, 9),  -- morten
                                                       (6, 12); -- maria


INSERT INTO public.expense (user_id, group_id, amount, description, created_at) VALUES
                                                                                    (1, 1, 847.50, 'Indkøb til weekenden - Føtex', '2025-06-15 10:30:00'),
                                                                                    (2, 1, 650.00, 'Benzin til turen', '2025-06-15 12:00:00'),
                                                                                    (3, 1, 4800.00, 'Leje af sommerhus 3 dage', '2025-06-14 15:00:00'),
                                                                                    (5, 1, 235.00, 'Morgenmad - bagværk og kaffe', '2025-06-16 08:00:00'),
                                                                                    (1, 1, 1250.00, 'Aftensmad og vin - Meny', '2025-06-16 19:00:00');

-- Norge Eventyr 2025 expenses
INSERT INTO public.expense (user_id, group_id, amount, description, created_at) VALUES
                                                                                    (4, 2, 12000.00, 'Flybilletter SAS København-Oslo', '2025-07-01 09:00:00'),
                                                                                    (6, 2, 3200.00, 'Hotel i Oslo - 2 nætter', '2025-07-05 14:30:00'),
                                                                                    (7, 2, 1450.00, 'Restaurant i Bergen', '2025-07-06 18:00:00'),
                                                                                    (8, 2, 890.00, 'Bergensbanen togbilletter', '2025-07-07 11:00:00'),
                                                                                    (4, 2, 1680.00, 'Dagligvarer - Rema 1000', '2025-07-08 10:00:00');

-- Copenhell Festival expenses
INSERT INTO public.expense (user_id, group_id, amount, description, created_at) VALUES
                                                                                    (1, 3, 5200.00, 'Copenhell 3-dages billetter (4 stk)', '2025-05-20 12:00:00'),
                                                                                    (4, 3, 520.00, 'Øl og mad dag 1', '2025-06-19 16:00:00'),
                                                                                    (9, 3, 450.00, 'T-shirts og plakater', '2025-06-20 14:30:00'),
                                                                                    (10, 3, 680.00, 'Øl og snacks dag 2', '2025-06-20 17:00:00');

-- Sommer i Sunny Beach expenses
INSERT INTO public.expense (user_id, group_id, amount, description, created_at) VALUES
                                                                                    (2, 4, 8500.00, 'Flybilletter til Burgas (5 personer)', '2025-08-01 10:00:00'),
                                                                                    (6, 4, 5400.00, 'Hotel booking 1 uge (5 personer)', '2025-08-05 09:00:00'),
                                                                                    (11, 4, 950.00, 'Restaurant ved stranden', '2025-08-06 20:00:00'),
                                                                                    (12, 4, 1200.00, 'Vandscooter udlejning', '2025-08-07 13:00:00'),
                                                                                    (13, 4, 485.00, 'Dagligvarer i lokalt supermarked', '2025-08-08 11:00:00'),
                                                                                    (2, 4, 1350.00, 'Natklub og drinks', '2025-08-08 23:00:00');

-- London baby expenses
INSERT INTO public.expense (user_id, group_id, amount, description, created_at) VALUES
                                                                                    (3, 5, 3600.00, 'Flybilletter København-London (3 stk)', '2025-09-10 08:00:00'),
                                                                                    (7, 5, 4200.00, 'Airbnb i Camden - 4 nætter', '2025-09-12 14:00:00'),
                                                                                    (14, 5, 450.00, 'British Museum souvenirs og frokost', '2025-09-13 12:30:00'),
                                                                                    (3, 5, 720.00, 'Pubbesøg og fish & chips', '2025-09-13 19:00:00'),
                                                                                    (7, 5, 380.00, 'Morgenmad på café', '2025-09-14 09:00:00');

-- Oste weekend i Sydfrankrig expenses
INSERT INTO public.expense (user_id, group_id, amount, description, created_at) VALUES
                                                                                    (5, 6, 15500.00, 'Flybilletter til Nice (5 personer)', '2025-10-01 10:00:00'),
                                                                                    (1, 6, 8900.00, 'Villa leje i Provence - 1 uge', '2025-10-03 15:00:00'),
                                                                                    (6, 6, 2850.00, 'Vin og oste på lokalt marked', '2025-10-04 11:00:00'),
                                                                                    (9, 6, 3200.00, 'Restaurant med Michelin-stjerne', '2025-10-04 20:00:00'),
                                                                                    (12, 6, 450.00, 'Boulangerie og croissanter', '2025-10-05 08:30:00'),
                                                                                    (5, 6, 1850.00, 'Benzin til lejebil', '2025-10-05 14:00:00'),
                                                                                    (1, 6, 980.00, 'Marked i Aix-en-Provence', '2025-10-06 10:00:00');