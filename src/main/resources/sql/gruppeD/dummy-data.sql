/*

    Gruppe D Dummy Data
    Last Updated: 09/10-2025

*/

INSERT INTO roles (id, role_name) VALUES
(1, 'user'),
(2, 'admin');

INSERT INTO pin_categories (id, category_name) VALUES
(1, 'visited'),
(2, 'bucket'),
(3, 'hated')
ON CONFLICT DO NOTHING;

INSERT INTO cities (city_name, country, latitude, longitude) VALUES
('Paris', 'France', 48.8566, 2.3522),
('London', 'United Kingdom', 51.5074, -0.1278),
('Berlin', 'Germany', 52.5200, 13.4050),
('Copenhagen', 'Denmark', 55.6761, 12.5683),
('New York', 'USA', 40.7128, -74.0060),
('Los Angeles', 'USA', 34.0522, -118.2437),
('Tokyo', 'Japan', 35.6895, 139.6917),
('Beijing', 'China', 39.9042, 116.4074),
('Shanghai', 'China', 31.2304, 121.4737),
('Moscow', 'Russia', 55.7558, 37.6173),
('Rome', 'Italy', 41.9028, 12.4964),
('Madrid', 'Spain', 40.4168, -3.7038),
('Barcelona', 'Spain', 41.3851, 2.1734),
('Lisbon', 'Portugal', 38.7169, -9.1399),
('Oslo', 'Norway', 59.9139, 10.7522),
('Stockholm', 'Sweden', 59.3293, 18.0686),
('Helsinki', 'Finland', 60.1699, 24.9384),
('Warsaw', 'Poland', 52.2297, 21.0122),
('Prague', 'Czech Republic', 50.0755, 14.4378),
('Vienna', 'Austria', 48.2082, 16.3738),
('Brussels', 'Belgium', 50.8503, 4.3517),
('Amsterdam', 'Netherlands', 52.3676, 4.9041),
('Dublin', 'Ireland', 53.3331, -6.2489),
('Edinburgh', 'United Kingdom', 55.9533, -3.1883),
('Glasgow', 'United Kingdom', 55.8642, -4.2518),
('Istanbul', 'Turkey', 41.0082, 28.9784),
('Athens', 'Greece', 37.9838, 23.7275),
('Zurich', 'Switzerland', 47.3769, 8.5417),
('Geneva', 'Switzerland', 46.2044, 6.1432),
('Budapest', 'Hungary', 47.4979, 19.0402),
('Bucharest', 'Romania', 44.4268, 26.1025),
('Belgrade', 'Serbia', 44.8176, 20.4569),
('Sofia', 'Bulgaria', 42.6977, 23.3219),
('Hanoi', 'Vietnam', 21.0285, 105.8542),
('Ho Chi Minh City', 'Vietnam', 10.7769, 106.7009),
('Bangkok', 'Thailand', 13.7563, 100.5018),
('Singapore', 'Singapore', 1.3521, 103.8198),
('Sydney', 'Australia', -33.8688, 151.2093),
('Melbourne', 'Australia', -37.8136, 144.9631),
('Auckland', 'New Zealand', -36.8485, 174.7633),
('Toronto', 'Canada', 43.6532, -79.3832),
('Vancouver', 'Canada', 49.2827, -123.1207),
('Montreal', 'Canada', 45.5017, -73.5673),
('Mexico City', 'Mexico', 19.4326, -99.1332),
('Buenos Aires', 'Argentina', -34.6037, -58.3816),
('Rio de Janeiro', 'Brazil', -22.9068, -43.1729),
('Sao Paulo', 'Brazil', -23.5505, -46.6333),
('Lagos', 'Nigeria', 6.5244, 3.3792),
('Cape Town', 'South Africa', -33.9249, 18.4241),
('Johannesburg', 'South Africa', -26.2041, 28.0473),
('Cairo', 'Egypt', 30.0444, 31.2357),
('Dubai', 'UAE', 25.276987, 55.296249),
('Doha', 'Qatar', 25.2854, 51.5310);