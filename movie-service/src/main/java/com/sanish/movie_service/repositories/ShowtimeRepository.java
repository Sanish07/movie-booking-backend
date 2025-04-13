package com.sanish.movie_service.repositories;

import com.sanish.movie_service.entities.Movie;
import com.sanish.movie_service.entities.Screen;
import com.sanish.movie_service.entities.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShowtimeRepository extends JpaRepository<Showtime,Long>{

    List<Showtime> findAllByMovieAndScreen(Movie movie, Screen screen);

    Optional<Showtime> findByShowtimeNumber(String showtimeNumber);

    void deleteByShowtimeNumber(String showtimeNumber);
}
