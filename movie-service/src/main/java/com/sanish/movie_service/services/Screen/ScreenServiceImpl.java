package com.sanish.movie_service.services.Screen;

import com.sanish.movie_service.dtos.ResponseDtos.PagedResult;
import com.sanish.movie_service.entities.Screen;
import com.sanish.movie_service.exceptions.ResourceNotFoundException;
import com.sanish.movie_service.repositories.ScreenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ScreenServiceImpl implements ScreenService{

    private ScreenRepository screenRepository;

    @Autowired
    public ScreenServiceImpl(ScreenRepository screenRepository) {
        this.screenRepository = screenRepository;
    }

    @Override
    public PagedResult<Screen> getAllScreens(Integer pageNumber) {
        return null;
    }

    @Override
    public List<Screen> getScreenByScreenNumber(Integer screenNumber) {
        List<Screen> fetchedScreen = screenRepository.findAllByScreenNumber(screenNumber);
        return fetchedScreen.stream().toList();
    }
}
