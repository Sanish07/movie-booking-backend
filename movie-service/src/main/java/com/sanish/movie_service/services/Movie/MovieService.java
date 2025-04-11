package com.sanish.movie_service.services.Movie;

import com.sanish.movie_service.dtos.Movie.MovieDto;
import com.sanish.movie_service.dtos.ResponseDtos.PagedResult;

public interface MovieService {
    PagedResult<MovieDto> getAllMovies(Integer pageNumber);

    MovieDto getMovieByMovieNumber(String movieNumber);

    void addNewMovie(MovieDto movieDto);

    void updateMovieDetails(MovieDto movieDto);

    void deleteMovieByMovieNumber(String movieNumber);
}
