package com.sanish.movie_service.controllers;

import com.sanish.movie_service.entities.Screen;
import com.sanish.movie_service.services.Screen.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/screens", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ScreenController {

    private ScreenService screenService;

    @Autowired
    public ScreenController(ScreenService screenService) {
        this.screenService = screenService;
    }

    @GetMapping("/{screenNumber}")
    public ResponseEntity<List<Screen>> getSpecificScreenDetails(@PathVariable Integer screenNumber){
        List<Screen> fetchedScreens = screenService.getScreenByScreenNumber(screenNumber);

        return new ResponseEntity<>(fetchedScreens, HttpStatus.OK);
    }
}
