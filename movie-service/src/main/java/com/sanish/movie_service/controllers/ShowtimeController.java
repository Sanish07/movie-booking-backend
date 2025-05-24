package com.sanish.movie_service.controllers;

import com.sanish.movie_service.dtos.ResponseDtos.ErrorResponseDto;
import com.sanish.movie_service.dtos.ResponseDtos.SuccessResponseDto;
import com.sanish.movie_service.dtos.Showtime.ShowtimeDto;
import com.sanish.movie_service.dtos.Showtime.ShowtimeResponseDto;
import com.sanish.movie_service.services.Showtime.ShowtimeService;
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

import java.util.List;

@Tag(
        name="Movie Microservice Showtime Entity Endpoints",
        description = "Create, Read and Delete Showtimes corresponding to Theater Screens inside Movie Booking application"
)
@RestController
@RequestMapping(path = "/api/showtimes", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ShowtimeController {

    private ShowtimeService showtimeService;

    @Autowired
    public ShowtimeController(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    @Operation(
            summary = "Fetch All Showtime Details Available for a Theater's Screen",
            description = "REST API endpoint to Fetch All Showtime Details for a Theater Screen"
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
    @GetMapping
    public ResponseEntity<List<ShowtimeDto>> getAllShowtimes(
            @RequestParam("movie_number") String movieNumber,
            @RequestParam("theater_number") String theaterNumber,
            @RequestParam("screen_number") Integer screenNumber){
        List<ShowtimeDto> allShows = showtimeService
                .getAllShowtimesForMovieInTheater(movieNumber, theaterNumber, screenNumber);

        return new ResponseEntity<>(allShows, HttpStatus.OK);
    }

    @Operation(
            summary = "Fetch Showtime Details by Showtime Number",
            description = "REST API endpoint to Fetch a Showtime by Showtime Number"
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
    @GetMapping("/{showtimeNumber}")
    public ResponseEntity<ShowtimeResponseDto> getShow(@PathVariable String showtimeNumber){
//        simulateSlowResponse();
        ShowtimeResponseDto fetchedShow = showtimeService.getShowtimeByShowtimeNumber(showtimeNumber);
        return new ResponseEntity<>(fetchedShow, HttpStatus.OK);
    }

    @Operation(
            summary = "Fetch Showtime Details by Movie Number",
            description = "REST API endpoint to Fetch a Showtime by Movie Number"
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
    @GetMapping("/movie/{movieNumber}")
    public ResponseEntity<List<ShowtimeDto>> getAllMovieShowtime(@PathVariable String movieNumber){
        List<ShowtimeDto> fetchedShowtimes = showtimeService.getAllShowtimesForMovie(movieNumber);
        return new ResponseEntity<>(fetchedShowtimes,HttpStatus.OK);
    }

    @Operation(
            summary = "Add New Showtime Details",
            description = "REST API endpoint to add new showtime details corresponding to a Movie, Theater and Screen for Movie Booking Application"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status Not Found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
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
    public ResponseEntity<SuccessResponseDto> addNewShow(
            @RequestBody ShowtimeDto showtimeDto,
            @RequestParam("movie_number") String movieNumber,
            @RequestParam("theater_number") String theaterNumber,
            @RequestParam("screen_number") Integer screenNumber){
        showtimeService.addNewShowtime(showtimeDto, movieNumber, theaterNumber, screenNumber);
        return new ResponseEntity<>(
                new SuccessResponseDto("200","New Show Added Successfully!"),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Existing Showtime Details",
            description = "REST API endpoint to delete existing Showtime details for Movie Booking Application"
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
    @DeleteMapping("/{showtimeNumber}")
    public ResponseEntity<SuccessResponseDto> deleteShow(@PathVariable String showtimeNumber){
        showtimeService.deleteShowtime(showtimeNumber);
        return new ResponseEntity<>(
                new SuccessResponseDto("200","Show with showtimeNumber : " + showtimeNumber + ", deleted successfully!"),
                HttpStatus.OK);
    }

    void simulateSlowResponse(){
        try{
            Thread.sleep(6000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
