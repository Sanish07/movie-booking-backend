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

    public static Movie mapToMovie(MovieDto movieDto) {
        return Movie.builder()
                .movieNumber(movieDto.getMovieNumber())
                .title(movieDto.getTitle())
                .description(movieDto.getDescription())
                .genre(movieDto.getGenre())
                .durationMinutes(movieDto.getDurationMinutes())
                .releaseDate(movieDto.getReleaseDate())
                .language(movieDto.getLanguage())
                .posterUrl(movieDto.getPosterUrl())
                .build();
    }
}
