-- Insert Movies
INSERT INTO movies (title, description, genre, duration_minutes, release_date, language, poster_url)
VALUES
  ('Inception', 'A mind-bending thriller about dream invasion.', 'Sci-Fi', 148, '2010-07-16', 'English', 'https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_FMjpg_UX1000_.jpg'),
  ('Interstellar', 'Explorers travel through a wormhole in space.', 'Sci-Fi', 169, '2014-11-07', 'English', 'https://m.media-amazon.com/images/M/MV5BYzdjMDAxZGItMjI2My00ODA1LTlkNzItOWFjMDU5ZDJlYWY3XkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg'),
  ('Dangal', 'A former wrestler, decides to fulfill his dream of winning a gold medal for his country by training his daughters for the Commonwealth games.', 'Sport/Action', 161, '2016-12-23', 'Hindi', 'https://m.media-amazon.com/images/M/MV5BMTQ4MzQzMzM2Nl5BMl5BanBnXkFtZTgwMTQ1NzU3MDI@._V1_.jpg');

-- Insert Theaters
INSERT INTO theaters (name, location, total_screens)
VALUES
  ('Galaxy Cinemas', 'Mumbai', 2),
  ('PVR Cinemas', 'Delhi', 2);

-- Insert Screens
INSERT INTO screens (screen_number, capacity, theater_id)
VALUES
  (1, 120, 1),
  (2, 150, 1),
  (1, 100, 2),
  (2, 130, 2);

-- Insert Showtimes
INSERT INTO showtimes (movie_id, screen_id, start_time, end_time, price)
VALUES
  (1, 1, '2025-03-30 10:00:00', '2025-03-30 12:30:00', 250.00),
  (1, 2, '2025-03-30 14:00:00', '2025-03-30 16:30:00', 300.00),
  (2, 3, '2025-03-30 11:00:00', '2025-03-30 14:00:00', 280.00),
  (3, 4, '2025-03-30 15:00:00', '2025-03-30 18:00:00', 220.00);
