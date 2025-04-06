-- Add movie_number to movies
ALTER TABLE movies ADD COLUMN movie_number VARCHAR(50);

-- Add theater_number to theaters
ALTER TABLE theaters ADD COLUMN theater_number VARCHAR(50);

-- Add showtime_number to showtimes
ALTER TABLE showtimes ADD COLUMN showtime_number VARCHAR(50);

-- Populate movie_number (e.g., MV-001, MV-002...)
UPDATE movies SET movie_number = CONCAT('MV-', LPAD(id::text, 3, '0'));

-- Populate theater_number (e.g., TH-001, TH-002...)
UPDATE theaters SET theater_number = CONCAT('TH-', LPAD(id::text, 3, '0'));

-- Populate showtime_number (e.g., ST-001, ST-002...)
UPDATE showtimes SET showtime_number = CONCAT('ST-', LPAD(id::text, 3, '0'));
