package com.sanish.movie_service.controllers;

import com.sanish.movie_service.dtos.ResponseDtos.ErrorResponseDto;
import com.sanish.movie_service.dtos.ResponseDtos.PagedResult;
import com.sanish.movie_service.dtos.ResponseDtos.SuccessResponseDto;
import com.sanish.movie_service.dtos.Theater.TheaterDto;
import com.sanish.movie_service.services.Theater.TheaterService;
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
        name="Movie Microservice Theater Entity Endpoints",
        description = "Create, Read, Update and Delete Theater details inside Movie Booking application"
)
@RestController
@RequestMapping(path = "/api/theaters", produces = {MediaType.APPLICATION_JSON_VALUE})
public class TheaterController {

    private TheaterService theaterService;

    @Autowired
    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @Operation(
            summary = "Fetch All Theater Details",
            description = "REST API endpoint to Fetch All Theater Details by page"
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
    public ResponseEntity<PagedResult<TheaterDto>> getAllTheatersByPage(
            @RequestParam(name = "page", defaultValue = "1") Integer pageNumber){
        PagedResult<TheaterDto> fetchedTheaters = theaterService.getAllTheaters(pageNumber);
        return new ResponseEntity<>(fetchedTheaters, HttpStatus.OK);
    }

    @Operation(
            summary = "Fetch Specific Theater Details by Theater Number",
            description = "REST API endpoint to Fetch a Theater Details by Theater Number"
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
    @GetMapping("/{theaterNumber}")
    public ResponseEntity<TheaterDto> getSpecificTheater(
            @PathVariable String theaterNumber){
        TheaterDto theaterDto = theaterService.getTheaterByTheaterNumber(theaterNumber);
        return new ResponseEntity<>(theaterDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Add New Theater Details",
            description = "REST API endpoint to add new Theater details inside Movie Booking Application"
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
    public ResponseEntity<SuccessResponseDto> addNewTheater(@RequestBody TheaterDto theaterDto){
        theaterService.addNewTheater(theaterDto);

        return new ResponseEntity<>(
                new SuccessResponseDto(
                        "201",
                        "New Movie Added Successfully!"),
                HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update Theater Details",
            description = "REST API endpoint to update theater details for Movie Booking Application"
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
    public ResponseEntity<SuccessResponseDto> updateTheatreDetails(@RequestBody TheaterDto theaterDto){
        theaterService.updateTheaterDetails(theaterDto);

        return new ResponseEntity<>(
                new SuccessResponseDto(
                        "200",
                        "Theater Details Updated Successfully!"),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Theater Details",
            description = "REST API endpoint to delete existing Theater details for Movie Booking Application"
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
    public ResponseEntity<SuccessResponseDto> deleteTheaterDetails(@PathVariable String theaterNumber){
        theaterService.deleteTheaterByTheaterNumber(theaterNumber);

        return new ResponseEntity<>(
                new SuccessResponseDto(
                        "200",
                        "Theater Details for theaterNumber : "+theaterNumber+", deleted successfully!"
                ),
                HttpStatus.OK
        );
    }

}
