package com.sanish.movie_service.services.Screen;

import com.sanish.movie_service.dtos.Screen.ScreenDto;
import com.sanish.movie_service.entities.Screen;
import com.sanish.movie_service.entities.Theater;
import com.sanish.movie_service.exceptions.ResourceAlreadyExistsException;
import com.sanish.movie_service.exceptions.ResourceNotFoundException;
import com.sanish.movie_service.mappers.ScreenMapper;
import com.sanish.movie_service.repositories.ScreenRepository;
import com.sanish.movie_service.repositories.TheaterRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class ScreenServiceImpl implements ScreenService{

    private TheaterRepository theaterRepository;
    private ScreenRepository screenRepository;

    @Autowired
    public ScreenServiceImpl(TheaterRepository theaterRepository, ScreenRepository screenRepository) {
        this.theaterRepository = theaterRepository;
        this.screenRepository = screenRepository;
    }

    @Override
    public List<ScreenDto> getAllScreensByTheatreNumber(String theatreNumber) {
        Theater fetchedTheater = theaterRepository.findByTheaterNumber(theatreNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Theatre","theaterNumber",theatreNumber));

        List<ScreenDto> fetchedScreens = screenRepository
                .findAllByTheater(fetchedTheater)
                .stream()
                .map(ScreenMapper::mapToScreenDto)
                .toList();

        return fetchedScreens;
    }

    @Override
    public ScreenDto getByTheaterAndScreenNumber(String theatreNumber, Integer screenNumber) {
        Theater fetchedTheater = theaterRepository.findByTheaterNumber(theatreNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Theatre","theaterNumber",theatreNumber));

        Screen fetchedScreen = screenRepository.findByTheaterAndScreenNumber(fetchedTheater, screenNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Screen","screenNumber",screenNumber.toString()));

        return ScreenMapper.mapToScreenDto(fetchedScreen);
    }

    @Override
    public void addNewScreen(ScreenDto screenDto, String theatreNumber) {
        //See if theater present to add screen for.
        Theater fetchedTheater = theaterRepository.findByTheaterNumber(theatreNumber)
                .orElseThrow(()-> new ResourceNotFoundException("Theater","theaterNumber",theatreNumber));

        //Fetch all of its screens
        List<ScreenDto> fetchedScreens = screenRepository
                .findAllByTheater(fetchedTheater)
                .stream()
                .map(ScreenMapper::mapToScreenDto)
                .toList();

        //Check if new screen is already present
        Optional<ScreenDto> screenPresent = fetchedScreens.stream()
                .filter(s -> Objects.equals(s.getScreenNumber(), screenDto.getScreenNumber()))
                .findFirst();

        if(screenPresent.isPresent()){
            throw new ResourceAlreadyExistsException("Screen","screenNumber",screenDto.getScreenNumber().toString());
        }

        //Save new screen and bind it with a theater
        Screen convertedScreenObj = ScreenMapper.mapToScreen(screenDto);
        convertedScreenObj.setTheater(fetchedTheater);
        convertedScreenObj.setCreatedAt(LocalDateTime.now());
        convertedScreenObj.setUpdatedAt(LocalDateTime.now());
        screenRepository.save(convertedScreenObj);
    }


    @Override
    public void deleteScreenByTheaterAndScreenNumber(String theaterNumber, Integer screenNumber) {
        Theater fetchedTheater = theaterRepository.findByTheaterNumber(theaterNumber)
                .orElseThrow(()-> new ResourceNotFoundException("Theater","theaterNumber",theaterNumber));

        //Fetch all of its screens
        List<ScreenDto> fetchedScreens = screenRepository
                .findAllByTheater(fetchedTheater)
                .stream()
                .map(ScreenMapper::mapToScreenDto)
                .toList();

        //Check if new screen is present, if not throw exception
        Optional<ScreenDto> screenPresent = fetchedScreens.stream()
                .filter(s -> Objects.equals(s.getScreenNumber(), screenNumber))
                .findFirst();

        if(screenPresent.isEmpty()){
            throw new ResourceNotFoundException("Screen","screenNumber",screenNumber.toString());
        }

        screenRepository.deleteByTheaterAndScreenNumber(fetchedTheater,screenNumber);
    }
}
