package com.sanish.movie_service.controllers;

import com.sanish.movie_service.dtos.ResponseDtos.SuccessResponseDto;
import com.sanish.movie_service.dtos.Showtime.ShowtimeDto;
import com.sanish.movie_service.dtos.Showtime.ShowtimeResponseDto;
import com.sanish.movie_service.services.Showtime.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/showtimes", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ShowtimeController {

    private ShowtimeService showtimeService;

    @Autowired
    public ShowtimeController(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    @GetMapping
    public ResponseEntity<List<ShowtimeDto>> getAllShowtimes(
            @RequestParam("movie_number") String movieNumber,
            @RequestParam("theater_number") String theaterNumber,
            @RequestParam("screen_number") Integer screenNumber){
        List<ShowtimeDto> allShows = showtimeService
                .getAllShowtimesForMovieInTheater(movieNumber, theaterNumber, screenNumber);

        return new ResponseEntity<>(allShows, HttpStatus.OK);
    }

    @GetMapping("/{showtimeNumber}")
    public ResponseEntity<ShowtimeResponseDto> getShow(@PathVariable String showtimeNumber){
        simulateSlowResponse();
        ShowtimeResponseDto fetchedShow = showtimeService.getShowtimeByShowtimeNumber(showtimeNumber);
        return new ResponseEntity<>(fetchedShow, HttpStatus.OK);
    }

    @GetMapping("/movie/{movieNumber}")
    public ResponseEntity<List<ShowtimeDto>> getAllMovieShowtime(@PathVariable String movieNumber){
        List<ShowtimeDto> fetchedShowtimes = showtimeService.getAllShowtimesForMovie(movieNumber);
        return new ResponseEntity<>(fetchedShowtimes,HttpStatus.OK);
    }

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
