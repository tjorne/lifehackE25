/*

    Gruppe D Tables
    Last Updated: 10/10-2025

    Should be 3NF.

    Insert as new Database named "Beyondborders" and it should work as intended.

*/

CREATE TABLE roles (
id SERIAL PRIMARY KEY,
role_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE users (
id SERIAL PRIMARY KEY,
email VARCHAR(255) UNIQUE NOT NULL,
username VARCHAR(100) UNIQUE NOT NULL,
password_hash TEXT NOT NULL,
role_id INT NOT NULL REFERENCES roles(id) ON DELETE RESTRICT,
created_at TIMESTAMP DEFAULT NOW(),
notifications BOOLEAN DEFAULT TRUE,
lang VARCHAR(10) DEFAULT 'en' NOT NULL
);

CREATE TABLE pin_categories (
id SERIAL PRIMARY KEY,
category_name VARCHAR(50) UNIQUE NOT NULL /* V OR BM */
);

CREATE TABLE pin_notes (
id SERIAL PRIMARY KEY,
user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
pin_title VARCHAR(100) NOT NULL,
pin_description VARCHAR(500) NOT NULL,
pin_rating INT NOT NULL
);

CREATE TABLE pins (
id SERIAL PRIMARY KEY,
user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
category_id INT NOT NULL REFERENCES pin_categories(id) ON DELETE RESTRICT,
title VARCHAR(255) NOT NULL,
rating INT CHECK (rating >= 0 AND rating <= 6),
latitude DECIMAL(9,6) NOT NULL,
longitude DECIMAL(9,6) NOT NULL,
created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE cities (
id SERIAL PRIMARY KEY,
city_name VARCHAR(100) NOT NULL,
country VARCHAR(100) NOT NULL,
latitude DECIMAL(9,6) NOT NULL,
longitude DECIMAL(9,6) NOT NULL,
created_at TIMESTAMP DEFAULT NOW()
);