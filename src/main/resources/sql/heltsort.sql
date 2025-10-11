-- Drop tables if they exist (for a clean setup, optional)
DROP TABLE IF EXISTS vaegtype CASCADE;
DROP TABLE IF EXISTS gips CASCADE;
DROP TABLE IF EXISTS beton CASCADE;
DROP TABLE IF EXISTS premalet CASCADE;

-- Create Gips table
CREATE TABLE gips (
                      gips_id   SERIAL PRIMARY KEY,
                      med_filt  INTEGER NOT NULL,
                      uden_filt INTEGER NOT NULL
);

-- Create Beton table
CREATE TABLE beton (
                       beton_id   SERIAL PRIMARY KEY,
                       med_filt   INTEGER NOT NULL,
                       uden_filt  INTEGER NOT NULL
);

-- Create premalet  table (was "eksisterende")
CREATE TABLE premalet (
                          premalet_id SERIAL PRIMARY KEY,
                          lag_1g        INTEGER NOT NULL,
                          lag_2g        INTEGER NOT NULL
);

-- Create Vaegtype table (main table)
CREATE TABLE vaegtype (
                          vaegtype_id     SERIAL PRIMARY KEY,
                          gips_id         INTEGER REFERENCES gips(gips_id) ON DELETE CASCADE,
                          beton_id        INTEGER REFERENCES beton(beton_id) ON DELETE CASCADE,
                          premalet_id   INTEGER REFERENCES premalet(premalet_id) ON DELETE CASCADE
);

-- Insert starting values
-- Insert Gips
INSERT INTO gips (med_filt, uden_filt)
VALUES (210, 170);

-- Insert Beton
INSERT INTO beton (med_filt, uden_filt)
VALUES (200, 160);

-- Insert Premalet
INSERT INTO premalet (lag_1g, lag_2g)
VALUES (45, 70);

-- Link them all in Vaegtype
INSERT INTO vaegtype (gips_id, beton_id, premalet_id)
VALUES (1, 1, 1);
