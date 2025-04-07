package com.sanish.movie_service.repositories;

import com.sanish.movie_service.entities.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest(
        properties = {
                "spring.test.database.replace=none",
                "spring.datasource.url=jdbc:tc:postgresql:17-alpine:///db"
        })
class MovieRepositoryTest {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieRepositoryTest(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Test
    void shouldFetchAllMovies(){
        List<Movie> movies = movieRepository.findAll();
        assertThat(movies).hasSize(3);
    }

    @Test
    void shouldFetchMovieByMovieNumber(){
        Movie movie = movieRepository.findByMovieNumber("MV-002").orElseThrow();
        assertThat(movie.getTitle()).isEqualTo("Interstellar");
        assertThat(movie.getDescription()).isEqualTo("Explorers travel through a wormhole in space.");
        assertThat(movie.getLanguage()).isEqualTo("English");
        assertThat(movie.getGenre()).isEqualTo("Sci-Fi");
        assertThat(movie.getDurationMinutes()).isEqualTo(169);
    }

    @Test
    void shouldReturnEmptyForInvalidMovieNumber(){
        assertThat(movieRepository.findByMovieNumber("MV-737")).isEmpty();
    }
}