package com.sanish.movie_service.dtos.Movie;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MovieDto {
    private String movieNumber;

    private String title;

    private String description;

    private String genre;

    private Integer durationMinutes;

    private LocalDate releaseDate;

    private String language;

    private String posterUrl;
}
