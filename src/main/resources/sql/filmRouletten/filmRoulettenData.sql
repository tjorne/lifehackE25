BEGIN;

-- MOVIES
INSERT INTO public.movie_roulette (movie_title, movie_aired, movie_description, movie_length, movie_rating) VALUES
                                                                                                                ('Inception', '2010-07-16', 'En tyv der stjæler hemmeligheder gennem drømme får en chance for forløsning.', 148, 9),
                                                                                                                ('The Dark Knight', '2008-07-18', 'Batman konfronterer Jokeren i et moralsk kaosfyldt Gotham.', 152, 9),
                                                                                                                ('Interstellar', '2014-11-07', 'Astronauter rejser gennem et ormehul for at redde menneskeheden.', 169, 8),
                                                                                                                ('The Matrix', '1999-03-31', 'En hacker opdager at virkeligheden er en simulation kontrolleret af maskiner.', 136, 10),
                                                                                                                ('Gladiator', '2000-05-05', 'En romersk general forrådt og tvunget til at kæmpe som gladiator for hævn.', 155, 8),
                                                                                                                ('Pulp Fiction', '1994-10-14', 'Flere sammenflettede historier om kriminalitet og moral i Los Angeles.', 154, 9),
                                                                                                                ('Avatar', '2009-12-18', 'En soldat i en fjern verden bliver splittet mellem pligt og naturfolkets kamp.', 162, 8),
                                                                                                                ('The Shawshank Redemption', '1994-09-23', 'To fanger knytter et usandsynligt venskab i Shawshank-fængslet.', 142, 10),
                                                                                                                ('The Godfather', '1972-03-24', 'Mafiafamilien Corleones magt, ære og forræderi i 1940ernes New York.', 175, 10),
                                                                                                                ('Spider-Man: No Way Home', '2021-12-17', 'Peter Parker kæmper mod multiversets skurke efter hans identitet afsløres.', 148, 8),
                                                                                                                ('The Avengers', '2012-05-04', 'Superhelte samles for at redde verden mod en kosmisk trussel.', 143, 8),
                                                                                                                ('Shrek', '2001-05-18', 'En grøn trold redder en prinsesse og finder venskab og kærlighed undervejs.', 90, 7);

-- GENRE MAPPING (bemærk: genre_roulette har kolonner movie_id, genre_name)
INSERT INTO public.genre_roulette (movie_id, genre_name) VALUES
                                                             (1, 'Sci-Fi'), (1, 'Thriller'),
                                                             (2, 'Action'), (2, 'Crime'),
                                                             (3, 'Drama'), (3, 'Adventure'),
                                                             (4, 'Sci-Fi'), (4, 'Action'),
                                                             (5, 'Action'), (5, 'Drama'),
                                                             (6, 'Crime'), (6, 'Drama'),
                                                             (7, 'Adventure'), (7, 'Fantasy'),
                                                             (8, 'Drama'),
                                                             (9, 'Crime'), (9, 'Drama'),
                                                             (10, 'Action'), (10, 'Fantasy'),
                                                             (11, 'Action'), (11, 'Adventure'),
                                                             (12, 'Comedy'), (12, 'Family');

-- PROVIDER NAMES
INSERT INTO public.provider_name_roulette (provider_name) VALUES
                                                              ('Netflix'),
                                                              ('HBO Max'),
                                                              ('Disney+'),
                                                              ('Amazon Prime'),
                                                              ('Paramount+');

-- PROVIDERS (provider_name_id er serial i provider_name_roulette, så 1=Netflix osv.)
INSERT INTO public.provider_roulette (provider_name_id, movie_id, provider_url) VALUES
                                                                                    (1, 1, 'https://www.netflix.com/title/70131314'),
                                                                                    (2, 2, 'https://www.hbomax.com/the-dark-knight'),
                                                                                    (5, 3, 'https://www.paramountplus.com/interstellar'),
                                                                                    (4, 4, 'https://www.amazon.com/matrix'),
                                                                                    (3, 5, 'https://www.disneyplus.com/gladiator'),
                                                                                    (4, 6, 'https://www.amazon.com/pulpfiction'),
                                                                                    (3, 7, 'https://www.disneyplus.com/avatar'),
                                                                                    (1, 8, 'https://www.netflix.com/title/70005379'),
                                                                                    (2, 9, 'https://www.hbomax.com/godfather'),
                                                                                    (1, 10, 'https://www.netflix.com/title/81026441'),
                                                                                    (3, 11, 'https://www.disneyplus.com/avengers'),
                                                                                    (1, 12, 'https://www.netflix.com/title/60004485');

-- USERS
INSERT INTO public.user_roulette (username, password) VALUES
                                                          ('Oliver', '1234'),
                                                          ('Maja', 'abcd'),
                                                          ('Jonas', 'pass123'),
                                                          ('Emil', 'qwerty'),
                                                          ('Laura', 'letmein'),
                                                          ('Sofie', 'test123'),
                                                          ('Magnus', 'abc123');

-- WATCHLIST (brug user_id og movie_id; antager serial startede ved 1)
INSERT INTO public.watchlist_roulette (user_id, movie_id) VALUES
                                                              (1, 1),
                                                              (1, 3),
                                                              (1, 7),
                                                              (2, 2),
                                                              (2, 8),
                                                              (3, 5),
                                                              (3, 6),
                                                              (4, 9),
                                                              (5, 4),
                                                              (5, 10),
                                                              (6, 11),
                                                              (7, 12);

COMMIT;
