package com.sanish.movie_service.services.Movie;

import com.sanish.movie_service.dtos.ResponseDtos.PagedResult;
import com.sanish.movie_service.dtos.Movie.MovieDto;
import com.sanish.movie_service.entities.Movie;
import com.sanish.movie_service.exceptions.ResourceAlreadyExistsException;
import com.sanish.movie_service.exceptions.ResourceNotFoundException;
import com.sanish.movie_service.mappers.MovieMapper;
import com.sanish.movie_service.repositories.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public PagedResult<MovieDto> getAllMovies(Integer pageNumber) {
        Sort sortFilter = Sort.by("title").ascending(); //Creating sorting filter for paged result, title is the column name of Movie entity
        pageNumber = (pageNumber <= 1) ? 0 : pageNumber - 1;
        Pageable pageable = PageRequest.of(pageNumber, 10, sortFilter); //Config page option

        Page<MovieDto> fetchedMovieList = movieRepository
                .findAll(pageable)
                .map(MovieMapper::mapToMovieDto); //Fetching all movies by page number

        PagedResult<MovieDto> moviePagedResult = new PagedResult<>(
                fetchedMovieList.getContent(),
                fetchedMovieList.getTotalElements(),
                fetchedMovieList.getNumber() + 1,
                fetchedMovieList.getTotalPages(),
                fetchedMovieList.isFirst(),
                fetchedMovieList.isLast(),
                fetchedMovieList.hasNext(),
                fetchedMovieList.hasPrevious()
        );

        return moviePagedResult;
    }

    @Override
    public MovieDto getMovieByMovieNumber(String movieNumber) {
        MovieDto fetchedMovie = movieRepository.findByMovieNumber(movieNumber).map(MovieMapper::mapToMovieDto)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "movieNumber", movieNumber));

        return fetchedMovie;
    }

    @Override
    public void addNewMovie(MovieDto movieDto) {
        //Check if new movie addition request number already exists in database
        Optional<Movie> movie = movieRepository.findByMovieNumber(movieDto.getMovieNumber());

        //If movie already exists with requested movie number, throw exception
        if(movie.isPresent()){
            throw new ResourceAlreadyExistsException("Movie", "movieNumber", movieDto.getMovieNumber());
        }

        Movie convertedMovieObj = MovieMapper.mapToMovie(movieDto);
        convertedMovieObj.setCreatedAt(LocalDateTime.now());
        convertedMovieObj.setUpdatedAt(LocalDateTime.now());

        movieRepository.save(convertedMovieObj);
    }

    @Override
    public void updateMovieDetails(MovieDto movieDto) {
        Optional<Movie> movie = movieRepository.findByMovieNumber(movieDto.getMovieNumber());

        if(movie.isEmpty()){
            throw new ResourceNotFoundException("Movie", "movieNumber", movieDto.getMovieNumber());
        }

        Movie convertedMovieObj = MovieMapper.mapToMovie(movieDto);
        convertedMovieObj.setId(movie.get().getId());
        convertedMovieObj.setUpdatedAt(LocalDateTime.now());
        convertedMovieObj.setCreatedAt(movie.get().getCreatedAt());

        movieRepository.save(convertedMovieObj);
    }

    @Override
    public void deleteMovieByMovieNumber(String movieNumber) {
        Optional<Movie> movie = movieRepository.findByMovieNumber(movieNumber);

        if(movie.isEmpty()){
            throw new ResourceNotFoundException("Movie", "movieNumber", movieNumber);
        }

        movieRepository.deleteById(movie.get().getId());
    }

}
