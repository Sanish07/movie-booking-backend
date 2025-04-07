package com.sanish.movie_service.controllers;

import com.sanish.movie_service.dtos.Movie.MovieDto;
import com.sanish.movie_service.dtos.Movie.PagedResult;
import com.sanish.movie_service.entities.Movie;
import com.sanish.movie_service.services.Movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
class MovieController {

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

    @GetMapping("/{movieNumber}")
    public ResponseEntity<MovieDto> getMovieByMovieNumber(@PathVariable String movieNumber){
        MovieDto fetchedMovie = movieService.getMovieByMovieNumber(movieNumber);
        return new ResponseEntity<>(fetchedMovie, HttpStatus.OK);
    }
}
