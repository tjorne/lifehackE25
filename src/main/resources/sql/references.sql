-- ==========================================
-- SAFE DATABASE SEED SCRIPT (FULL + FIXED)
-- ==========================================

-- Use public schema
SET search_path TO public;

-- ==========================================
-- DROP TABLES IF EXISTS
-- ==========================================
DROP TABLE IF EXISTS theorist_fields CASCADE;
DROP TABLE IF EXISTS theorist_books CASCADE;
DROP TABLE IF EXISTS themes CASCADE;
DROP TABLE IF EXISTS books CASCADE;
DROP TABLE IF EXISTS fields CASCADE;
DROP TABLE IF EXISTS theorists CASCADE;

-- ==========================================
-- TABLE DEFINITIONS
-- ==========================================

CREATE TABLE theorists (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           birthyear INT,
                           deathyear INT
);

CREATE TABLE fields (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        description TEXT
);

CREATE TABLE books (
                       id SERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       year INT,
                       field_id INT REFERENCES fields(id) DEFERRABLE INITIALLY DEFERRED,
                       description TEXT
);

CREATE TABLE themes (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        book_id INT REFERENCES books(id) DEFERRABLE INITIALLY DEFERRED,
                        description TEXT
);

CREATE TABLE theorist_books (
                                theorist_id INT REFERENCES theorists(id) DEFERRABLE INITIALLY DEFERRED,
                                book_id INT REFERENCES books(id) DEFERRABLE INITIALLY DEFERRED,
                                PRIMARY KEY (theorist_id, book_id)
);

CREATE TABLE theorist_fields (
                                 theorist_id INT REFERENCES theorists(id) DEFERRABLE INITIALLY DEFERRED,
                                 field_id INT REFERENCES fields(id) DEFERRABLE INITIALLY DEFERRED,
                                 PRIMARY KEY (theorist_id, field_id)
);

-- ==========================================
-- DATA INSERTION
-- ==========================================

BEGIN;

-- ===== TABLE: fields =====
INSERT INTO fields (id, name, description) VALUES
                                               (1, 'Philosophy', 'The study of fundamental questions about existence, knowledge, and values.'),
                                               (2, 'Psychology', 'The scientific study of the human mind and behavior.'),
                                               (3, 'Sociology', 'The study of social relationships, interactions, and culture.'),
                                               (4, 'Linguistics', 'The scientific study of language and its structure.'),
                                               (5, 'Political Science', 'The study of systems of governance and political activities.'),
                                               (6, 'Anthropology', 'The study of human societies, cultures, and their development.'),
                                               (7, 'Economics', 'The study of production, consumption, and transfer of wealth.'),
                                               (8, 'Data Science', 'The field focused on extracting insights from data.')
    ON CONFLICT (id) DO NOTHING;

-- ===== TABLE: theorists =====
INSERT INTO theorists (id, name, birthyear, deathyear) VALUES
                                                           (1, 'Sigmund Freud', 1856, 1939),
                                                           (2, 'Karl Marx', 1818, 1883),
                                                           (3, 'Friedrich Nietzsche', 1844, 1900),
                                                           (4, 'Émile Durkheim', 1858, 1917),
                                                           (5, 'Noam Chomsky', 1928, NULL),
                                                           (6, 'John Maynard Keynes', 1883, 1946),
                                                           (7, 'Claude Lévi-Strauss', 1908, 2009),
                                                           (8, 'Michel Foucault', 1926, 1984),
                                                           (9, 'Carl Jung', 1875, 1961),
                                                           (10, 'Hannah Arendt', 1906, 1975),
                                                           (11, 'Lieutenant Kirk', 2300, NULL)
    ON CONFLICT (id) DO NOTHING;

-- ===== TABLE: books =====
INSERT INTO books (id, title, year, field_id, description) VALUES
                                                               (1, 'The Interpretation of Dreams', 1899, 2, 'Freud’s foundational work on psychoanalysis and the unconscious mind.'),
                                                               (2, 'The Communist Manifesto', 1848, 1, 'Marx’s revolutionary text outlining class struggle and socialism.'),
                                                               (3, 'Thus Spoke Zarathustra', 1883, 1, 'Nietzsche’s philosophical novel introducing the concept of the Übermensch.'),
                                                               (4, 'The Division of Labour in Society', 1893, 3, 'Durkheim’s analysis of social order and solidarity.'),
                                                               (5, 'Syntactic Structures', 1957, 4, 'Chomsky’s groundbreaking work on generative grammar.'),
                                                               (6, 'The General Theory of Employment, Interest and Money', 1936, 7, 'Keynes’s macroeconomic theory of employment and government intervention.'),
                                                               (7, 'The Savage Mind', 1962, 6, 'Lévi-Strauss’s structuralist analysis of human thought.'),
                                                               (8, 'Discipline and Punish', 1975, 1, 'Foucault’s study of power, discipline, and societal control.'),
                                                               (9, 'Man and His Symbols', 1964, 2, 'Jung’s exploration of symbols and archetypes in the human mind.'),
                                                               (10, 'The Human Condition', 1958, 5, 'Arendt’s analysis of human activities, freedom, and political life.'),
                                                               (11, 'May the Force Be Forever in Your Favour', 2200, 8, 'A futuristic exploration of data, destiny, and decision-making.'),
                                                               (12, 'The Origins of Totalitarianism', 1951, 5, 'Arendt’s detailed study of the rise of totalitarian regimes.'),
                                                               (13, 'On Revolution', 1963, 5, 'Arendt’s work exploring freedom and political action.')
    ON CONFLICT (id) DO NOTHING;

-- ===== TABLE: themes =====
INSERT INTO themes (id, name, book_id, description) VALUES
                                                        (1, 'Unconscious Mind', 1, 'Explores hidden desires and dream interpretation.'),
                                                        (2, 'Class Struggle', 2, 'Examines the conflict between bourgeoisie and proletariat.'),
                                                        (3, 'Nihilism', 3, 'Questions traditional moral values and meaning in life.'),
                                                        (4, 'Social Solidarity', 4, 'Analyzes the bonds that connect individuals in society.'),
                                                        (5, 'Universal Grammar', 5, 'Proposes that the ability to acquire language is innate to humans.'),
                                                        (6, 'Macroeconomic Theory', 6, 'Explores how government policies can stabilize economies.'),
                                                        (7, 'Structuralism', 7, 'Investigates the underlying structures of human thought.'),
                                                        (8, 'Power and Discipline', 8, 'Explores how societal institutions control and regulate behavior.'),
                                                        (9, 'Collective Unconscious', 9, 'Examines shared symbols and archetypes across cultures.'),
                                                        (10, 'Political Action', 10, 'Discusses human freedom, labor, and participation in public life.'),
                                                        (11, 'May the Force Be Forever in Your Favour', 11, 'A futuristic exploration of data, destiny, and decision-making.'),
                                                        (12, 'Totalitarianism', 12, 'Analyzes the mechanisms that define and maintain totalitarian regimes.'),
                                                        (13, 'Human Freedom', 13, 'Explores the concept of freedom in the context of human action and politics.')
    ON CONFLICT (id) DO NOTHING;

-- ===== TABLE: theorist_books =====
INSERT INTO theorist_books (theorist_id, book_id) VALUES
                                                      (1, 1),
                                                      (2, 2),
                                                      (3, 3),
                                                      (4, 4),
                                                      (5, 5),
                                                      (6, 6),
                                                      (7, 7),
                                                      (8, 8),
                                                      (9, 9),
                                                      (10, 10),
                                                      (11, 11),
                                                      (10, 12),
                                                      (10, 13)
    ON CONFLICT (theorist_id, book_id) DO NOTHING;

-- ===== TABLE: theorist_fields =====
INSERT INTO theorist_fields (theorist_id, field_id) VALUES
                                                        (1, 2),
                                                        (2, 1),
                                                        (3, 1),
                                                        (4, 3),
                                                        (5, 4),
                                                        (6, 7),
                                                        (7, 6),
                                                        (8, 1),
                                                        (9, 2),
                                                        (10, 5),
                                                        (10, 1),
                                                        (11, 8)
    ON CONFLICT (theorist_id, field_id) DO NOTHING;

COMMIT;

