package com.sanish.movie_service.controllers;

import com.sanish.movie_service.dtos.ResponseDtos.ErrorResponseDto;
import com.sanish.movie_service.dtos.ResponseDtos.SuccessResponseDto;
import com.sanish.movie_service.dtos.Screen.ScreenDto;
import com.sanish.movie_service.entities.Screen;
import com.sanish.movie_service.services.Screen.ScreenService;
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
        name="Movie Microservice Screen Entity Endpoints",
        description = "Create, Read and Delete Screen Details inside Movie Booking application"
)
@RestController
@RequestMapping(path = "/api/screens", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ScreenController {

    private ScreenService screenService;

    @Autowired
    public ScreenController(ScreenService screenService) {
        this.screenService = screenService;
    }

    @Operation(
            summary = "Fetch All Screen Details Available for a Theater",
            description = "REST API endpoint to Fetch All Screen Details for a Theater"
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
    @GetMapping("/{theaterNumber}")
    public ResponseEntity<List<ScreenDto>> getAllTheaterScreens(@PathVariable String theaterNumber){
        List<ScreenDto> fetchedScreens = screenService.getAllScreensByTheatreNumber(theaterNumber);
        return new ResponseEntity<>(fetchedScreens, HttpStatus.OK);
    }

    @Operation(
            summary = "Fetch Particular Screen Details",
            description = "REST API endpoint to Fetch a Screen by Theater Number and Screen Number"
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
    @GetMapping
    public ResponseEntity<ScreenDto> getScreenDetails(
            @RequestParam("theater_number") String theaterNumber,
            @RequestParam("screen_number") Integer screenNumber){
        ScreenDto fetchedScreen = screenService.getByTheaterAndScreenNumber(theaterNumber, screenNumber);
        return new ResponseEntity<>(fetchedScreen, HttpStatus.OK);
    }

    @Operation(
            summary = "Add New Screen Details",
            description = "REST API endpoint to add new screen details corresponding to a Theater for Movie Booking Application"
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
    @PostMapping("/{theaterNumber}")
    public ResponseEntity<SuccessResponseDto> addNewScreenForTheater(
            @PathVariable String theaterNumber, @RequestBody ScreenDto screenDto){
        screenService.addNewScreen(screenDto, theaterNumber);
        return new ResponseEntity<>(
                new SuccessResponseDto("201", "New Screen for theaterNumber : "+ theaterNumber+", added successfully!"),
                HttpStatus.CREATED
        );
    }

    @Operation(
            summary = "Delete Screen Details",
            description = "REST API endpoint to delete existing screen details for Movie Booking Application"
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
    @DeleteMapping("/{theaterNumber}")
    public ResponseEntity<SuccessResponseDto> deleteTheatersScreen(
            @PathVariable String theaterNumber,
            @RequestParam("screen_number") Integer screenNumber){
        screenService.deleteScreenByTheaterAndScreenNumber(theaterNumber,screenNumber);
        return new ResponseEntity<>(
                new SuccessResponseDto("200", "Screen deleted successfully!"),
                HttpStatus.OK
        );
    }
}
