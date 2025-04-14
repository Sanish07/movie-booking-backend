package com.sanish.movie_service.services.Showtime;

import com.sanish.movie_service.dtos.Showtime.ShowtimeDto;
import com.sanish.movie_service.dtos.Showtime.ShowtimeResponseDto;
import com.sanish.movie_service.entities.Movie;
import com.sanish.movie_service.entities.Screen;
import com.sanish.movie_service.entities.Showtime;
import com.sanish.movie_service.entities.Theater;
import com.sanish.movie_service.exceptions.ResourceAlreadyExistsException;
import com.sanish.movie_service.exceptions.ResourceNotFoundException;
import com.sanish.movie_service.mappers.ShowtimeMapper;
import com.sanish.movie_service.repositories.MovieRepository;
import com.sanish.movie_service.repositories.ScreenRepository;
import com.sanish.movie_service.repositories.ShowtimeRepository;
import com.sanish.movie_service.repositories.TheaterRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ShowtimeServiceImpl implements ShowtimeService{

    private MovieRepository movieRepository;
    private TheaterRepository theaterRepository;
    private ScreenRepository screenRepository;
    private ShowtimeRepository showtimeRepository;

    @Autowired
    public ShowtimeServiceImpl(MovieRepository movieRepository, TheaterRepository theaterRepository, ScreenRepository screenRepository, ShowtimeRepository showtimeRepository) {
        this.movieRepository = movieRepository;
        this.theaterRepository = theaterRepository;
        this.screenRepository = screenRepository;
        this.showtimeRepository = showtimeRepository;
    }


    @Override
    public List<ShowtimeDto> getAllShowtimesForMovieInTheater(
            String movieNumber, String theaterNumber, Integer screenNumber) {

        Movie fetchedMovie = movieRepository.findByMovieNumber(movieNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Movie","movieNumber",movieNumber));

        Theater fetchedTheater = theaterRepository.findByTheaterNumber(theaterNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Theater","theaterNumber",theaterNumber));

        Screen fetchedScreen = screenRepository.findByTheaterAndScreenNumber(fetchedTheater, screenNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Screen","screenNumber",screenNumber.toString()));

        List<ShowtimeDto> fetchedShowtimes = showtimeRepository
                .findAllByMovieAndScreen(fetchedMovie,fetchedScreen)
                .stream()
                .map(ShowtimeMapper::mapToShowtimeDto)
                .toList();

        return fetchedShowtimes;
    }

    @Override
    public ShowtimeResponseDto getShowtimeByShowtimeNumber(String showtimeNumber) {
        Optional<Showtime> fetchedShowtime = showtimeRepository
                .findByShowtimeNumber(showtimeNumber);

        if(fetchedShowtime.isEmpty()){
            throw new ResourceNotFoundException("Showtime","showtimeNumber",showtimeNumber);
        }

        ShowtimeDto showtimeDto = ShowtimeMapper.mapToShowtimeDto(fetchedShowtime.get());

        Movie fetchedMovie = movieRepository.findById(fetchedShowtime.get().getMovie().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie","id",fetchedShowtime.get().getMovie().getId().toString()));

        Theater fetchedTheater = theaterRepository.findById(fetchedShowtime.get().getScreen().getTheater().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Theater","id",fetchedShowtime.get().getScreen().getTheater().getId().toString()));

        Screen fetchedScreen = screenRepository.findById(fetchedShowtime.get().getScreen().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Screen","id",fetchedShowtime.get().getScreen().getId().toString()));


        ShowtimeResponseDto showtimeResponseDto = new ShowtimeResponseDto(
                        showtimeDto, fetchedMovie.getTitle(), fetchedTheater.getName(), fetchedScreen.getScreenNumber());

        return showtimeResponseDto;
    }

    @Override
    public List<ShowtimeDto> getAllShowtimesForMovie(String movieNumber) {
        Movie fetchedMovie = movieRepository.findByMovieNumber(movieNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Movie","movieNumber",movieNumber));

        List<ShowtimeDto> fetchedShowtimes = showtimeRepository
                .findAllByMovie(fetchedMovie)
                .stream()
                .map(ShowtimeMapper::mapToShowtimeDto)
                .toList();

        return fetchedShowtimes;
    }


    @Override
    public void addNewShowtime(
            ShowtimeDto showtimeDto, String movieNumber, String theaterNumber, Integer screenNumber) {

        Optional<Showtime> fetchedShowtime = showtimeRepository.findByShowtimeNumber(showtimeDto.getShowtimeNumber());

        if(fetchedShowtime.isPresent()){
            throw new ResourceAlreadyExistsException("Showtime","showtime",showtimeDto.getShowtimeNumber());
        }

        Movie fetchedMovie = movieRepository.findByMovieNumber(movieNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Movie","movieNumber",movieNumber));

        Theater fetchedTheater = theaterRepository.findByTheaterNumber(theaterNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Theater","theaterNumber",theaterNumber));

        Screen fetchedScreen = screenRepository.findByTheaterAndScreenNumber(fetchedTheater, screenNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Screen","screenNumber",screenNumber.toString()));

        Showtime newShowtime = ShowtimeMapper.mapToShowtime(showtimeDto);
        newShowtime.setMovie(fetchedMovie);
        newShowtime.setScreen(fetchedScreen);
        newShowtime.setCreatedAt(LocalDateTime.now());
        newShowtime.setUpdatedAt(LocalDateTime.now());

        showtimeRepository.save(newShowtime);
    }

    @Override
    public void deleteShowtime(String showtimeNumber) {
        Optional<Showtime> fetchedShowtime = showtimeRepository.findByShowtimeNumber(showtimeNumber);

        if(fetchedShowtime.isEmpty()){
            throw new ResourceNotFoundException("Showtime","showtimeNumber",showtimeNumber);
        }

        showtimeRepository.deleteByShowtimeNumber(showtimeNumber);
    }
}
