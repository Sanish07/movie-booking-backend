package com.sanish.movie_service.controllers;

import com.sanish.movie_service.dtos.Movie.MovieDto;
import com.sanish.movie_service.dtos.Movie.PagedResult;
import com.sanish.movie_service.entities.Movie;
import com.sanish.movie_service.services.Movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<PagedResult<MovieDto>> getAllMovies(
            @RequestParam(name = "page", defaultValue = "1") Integer pageNumber){
        PagedResult<MovieDto> fetchedMovies = movieService.getAllMovies(pageNumber);
        return new ResponseEntity<>(fetchedMovies, HttpStatus.OK);
    }
}
