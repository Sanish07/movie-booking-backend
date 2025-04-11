package com.sanish.movie_service.controllers;

import com.sanish.movie_service.dtos.ResponseDtos.PagedResult;
import com.sanish.movie_service.dtos.ResponseDtos.SuccessResponseDto;
import com.sanish.movie_service.dtos.Theater.TheaterDto;
import com.sanish.movie_service.services.Theater.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/theaters", produces = {MediaType.APPLICATION_JSON_VALUE})
public class TheaterController {

    private TheaterService theaterService;

    @Autowired
    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @GetMapping
    public ResponseEntity<PagedResult<TheaterDto>> getAllTheatersByPage(
            @RequestParam(name = "page", defaultValue = "1") Integer pageNumber){
        PagedResult<TheaterDto> fetchedTheaters = theaterService.getAllTheaters(pageNumber);
        return new ResponseEntity<>(fetchedTheaters, HttpStatus.OK);
    }

    @GetMapping("/{theaterNumber}")
    public ResponseEntity<TheaterDto> getSpecificTheater(
            @PathVariable String theaterNumber){
        TheaterDto theaterDto = theaterService.getTheaterByTheaterNumber(theaterNumber);
        return new ResponseEntity<>(theaterDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SuccessResponseDto> addNewTheater(@RequestBody TheaterDto theaterDto){
        theaterService.addNewTheater(theaterDto);

        return new ResponseEntity<>(
                new SuccessResponseDto(
                        "201",
                        "New Movie Added Successfully!"),
                HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<SuccessResponseDto> updateTheatreDetails(@RequestBody TheaterDto theaterDto){
        theaterService.updateTheaterDetails(theaterDto);

        return new ResponseEntity<>(
                new SuccessResponseDto(
                        "200",
                        "Theater Details Updated Successfully!"),
                HttpStatus.OK);
    }

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
