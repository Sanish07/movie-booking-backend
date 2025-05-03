package com.sanish.movie_service.controllers;

import com.sanish.movie_service.dtos.Movie.MovieDto;
import com.sanish.movie_service.dtos.ResponseDtos.ErrorResponseDto;
import com.sanish.movie_service.dtos.ResponseDtos.PagedResult;
import com.sanish.movie_service.dtos.ResponseDtos.SuccessResponseDto;
import com.sanish.movie_service.services.Movie.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name="Movie Microservice Movie Entity Endpoints",
        description = "Create, Read, Update, Delete Movies inside Movie Booking application"
)
@RestController
@RequestMapping(path = "/api/movies", produces = {MediaType.APPLICATION_JSON_VALUE})
class MovieController {

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @Operation(
            summary = "Fetch All Movie Details",
            description = "REST API endpoint to Fetch All Movies by Page"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping
    public ResponseEntity<PagedResult<MovieDto>> getAllMovies(
            @RequestParam(name = "page", defaultValue = "1") Integer pageNumber){
        PagedResult<MovieDto> fetchedMovies = movieService.getAllMovies(pageNumber);
        return new ResponseEntity<>(fetchedMovies, HttpStatus.OK);
    }

    @Operation(
            summary = "Fetch Particular Movie Details",
            description = "REST API endpoint to Fetch a Movie by Movie Number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status Not Found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/{movieNumber}")
    public ResponseEntity<MovieDto> getMovieByMovieNumber(@PathVariable String movieNumber){
        MovieDto fetchedMovie = movieService.getMovieByMovieNumber(movieNumber);
        return new ResponseEntity<>(fetchedMovie, HttpStatus.OK);
    }

    @Operation(
            summary = "Add New Movie Details",
            description = "REST API endpoint to add new movie details for Movie Booking Application"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "HTTP Status CONFLICT",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping
    public ResponseEntity<SuccessResponseDto> createNewMovie(@RequestBody MovieDto movieDto){

        movieService.addNewMovie(movieDto); //Everything will be validated and handled in Service Layer

        return new ResponseEntity<>(
                new SuccessResponseDto("201","New Movie Added Successfully!"),
                HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update Movie Details",
            description = "REST API endpoint to update movie details for Movie Booking Application"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status Not Found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PutMapping
    public ResponseEntity<SuccessResponseDto> updateMovieDetails(@RequestBody MovieDto movieDto){

        movieService.updateMovieDetails(movieDto);

        return new ResponseEntity<>(
                new SuccessResponseDto("200","Movie Details Updated Successfully!"),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "Delete Movie Details",
            description = "REST API endpoint to delete existing movie details for Movie Booking Application"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status Not Found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @DeleteMapping("/{movieNumber}")
    public ResponseEntity<SuccessResponseDto> deleteMovie(@PathVariable String movieNumber){
        movieService.deleteMovieByMovieNumber(movieNumber);

        return new ResponseEntity<>(
                new SuccessResponseDto("200", "Movie Details for movieNumber : " + movieNumber + " deleted successfully!"),
                HttpStatus.OK
        );
    }
}
