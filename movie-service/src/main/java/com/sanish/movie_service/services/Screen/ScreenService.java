package com.sanish.movie_service.services.Screen;

import com.sanish.movie_service.dtos.Movie.MovieDto;
import com.sanish.movie_service.dtos.ResponseDtos.PagedResult;
import com.sanish.movie_service.dtos.Screen.ScreenDto;
import com.sanish.movie_service.entities.Screen;
import java.util.*;

public interface ScreenService {
    List<ScreenDto> getAllScreensByTheatreNumber(String theatreNumber);

    void addNewScreen(ScreenDto screenDto, String theatreNumber);

    void deleteScreenByTheaterAndScreenNumber(String theaterNumber,Integer screenNumber);
}
