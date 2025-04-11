package com.sanish.movie_service.services.Screen;

import com.sanish.movie_service.dtos.Movie.MovieDto;
import com.sanish.movie_service.dtos.ResponseDtos.PagedResult;
import com.sanish.movie_service.entities.Screen;
import java.util.*;

public interface ScreenService {
    PagedResult<Screen> getAllScreens(Integer pageNumber);

    List<Screen> getScreenByScreenNumber(Integer screenNumber);

}
