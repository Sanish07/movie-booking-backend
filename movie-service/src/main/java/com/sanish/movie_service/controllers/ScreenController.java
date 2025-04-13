package com.sanish.movie_service.controllers;

import com.sanish.movie_service.dtos.ResponseDtos.SuccessResponseDto;
import com.sanish.movie_service.dtos.Screen.ScreenDto;
import com.sanish.movie_service.entities.Screen;
import com.sanish.movie_service.services.Screen.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/screens", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ScreenController {

    private ScreenService screenService;

    @Autowired
    public ScreenController(ScreenService screenService) {
        this.screenService = screenService;
    }

    @GetMapping("/{theaterNumber}")
    public ResponseEntity<List<ScreenDto>> getAllTheaterScreens(@PathVariable String theaterNumber){
        List<ScreenDto> fetchedScreens = screenService.getAllScreensByTheatreNumber(theaterNumber);
        return new ResponseEntity<>(fetchedScreens, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ScreenDto> getScreenDetails(
            @RequestParam("theater_number") String theaterNumber,
            @RequestParam("screen_number") Integer screenNumber){
        ScreenDto fetchedScreen = screenService.getByTheaterAndScreenNumber(theaterNumber, screenNumber);
        return new ResponseEntity<>(fetchedScreen, HttpStatus.OK);
    }

    @PostMapping("/{theaterNumber}")
    public ResponseEntity<SuccessResponseDto> addNewScreenForTheater(
            @PathVariable String theaterNumber, @RequestBody ScreenDto screenDto){
        screenService.addNewScreen(screenDto, theaterNumber);
        return new ResponseEntity<>(
                new SuccessResponseDto("201", "New Screen for theaterNumber : "+ theaterNumber+", added successfully!"),
                HttpStatus.CREATED
        );
    }

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
