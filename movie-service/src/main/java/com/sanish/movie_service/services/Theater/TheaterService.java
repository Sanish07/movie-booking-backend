package com.sanish.movie_service.services.Theater;

import com.sanish.movie_service.dtos.Movie.MovieDto;
import com.sanish.movie_service.dtos.Movie.PagedResult;
import com.sanish.movie_service.dtos.Theater.TheaterDto;

public interface TheaterService {
    PagedResult<TheaterDto> getAllTheaters(Integer pageNumber);

    TheaterDto getTheaterByTheaterNumber(String theaterNumber);

    void addNewTheater(TheaterDto theaterDto);

    void updateTheaterDetails(TheaterDto theaterDto);

    void deleteTheaterByTheaterNumber(String theaterNumber);
}
