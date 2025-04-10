package com.sanish.movie_service.controllers;

import com.sanish.movie_service.dtos.Movie.PagedResult;
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

}
