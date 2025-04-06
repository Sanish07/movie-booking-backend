package com.sanish.movie_service.services.Movie;

import com.sanish.movie_service.dtos.Movie.MovieDto;
import com.sanish.movie_service.dtos.Movie.PagedResult;
import com.sanish.movie_service.entities.Movie;

public interface MovieService {
    PagedResult<MovieDto> getAllMovies(Integer pageNumber);

    MovieDto getMovieById(Long id);

    void addNewMovie(MovieDto movieDto);

    void updateMovieDetails(MovieDto movieDto);

    void deleteMovieById(Long id);
}
