package com.sanish.movie_service.controllers;

import com.sanish.movie_service.dtos.Movie.MovieDto;
import com.sanish.movie_service.dtos.ResponseDtos.PagedResult;
import com.sanish.movie_service.dtos.ResponseDtos.SuccessResponseDto;
import com.sanish.movie_service.services.Movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/movies", produces = {MediaType.APPLICATION_JSON_VALUE})
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

    @PostMapping
    public ResponseEntity<SuccessResponseDto> createNewMovie(@RequestBody MovieDto movieDto){

        movieService.addNewMovie(movieDto); //Everything will be validated and handled in Service Layer

        return new ResponseEntity<>(
                new SuccessResponseDto("201","New Movie Added Successfully!"),
                HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<SuccessResponseDto> updateMovieDetails(@RequestBody MovieDto movieDto){

        movieService.updateMovieDetails(movieDto);

        return new ResponseEntity<>(
                new SuccessResponseDto("200","Movie Details Updated Successfully!"),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{movieNumber}")
    public ResponseEntity<SuccessResponseDto> deleteMovie(@PathVariable String movieNumber){
        movieService.deleteMovieByMovieNumber(movieNumber);

        return new ResponseEntity<>(
                new SuccessResponseDto("200", "Movie Details for movieNumber : " + movieNumber + " deleted successfully!"),
                HttpStatus.OK
        );
    }
}
