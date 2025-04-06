package com.sanish.movie_service.mappers;

import com.sanish.movie_service.dtos.Movie.MovieDto;
import com.sanish.movie_service.entities.Movie;

public class MovieMapper {

    public static MovieDto mapToMovieDto(Movie movie){
        return new MovieDto(
            movie.getMovieNumber(),
            movie.getTitle(),
            movie.getDescription(),
            movie.getGenre(),
            movie.getDurationMinutes(),
            movie.getReleaseDate(),
            movie.getLanguage(),
            movie.getPosterUrl()
        );
    }
}
