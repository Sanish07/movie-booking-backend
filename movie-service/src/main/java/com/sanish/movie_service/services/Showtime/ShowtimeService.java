package com.sanish.movie_service.services.Showtime;

import com.sanish.movie_service.dtos.Showtime.ShowtimeDto;
import com.sanish.movie_service.dtos.Showtime.ShowtimeResponseDto;

import java.util.List;

public interface ShowtimeService {

    List<ShowtimeDto> getAllShowtimesForMovieInTheater(String movieNumber, String theaterNumber, Integer screenNumber);

    ShowtimeResponseDto getShowtimeByShowtimeNumber(String showtimeNumber);

    void addNewShowtime(ShowtimeDto showtimeDto, String movieNumber, String theaterNumber, Integer screenNumber);

    void deleteShowtime(String showtimeNumber);
}
