package com.sanish.movie_service.repositories;

import com.sanish.movie_service.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie,Long>{
    Optional<Movie> findByMovieNumber(String movieNumber);
}
