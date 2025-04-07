package com.sanish.movie_service.services.Movie;

import com.sanish.movie_service.dtos.Movie.PagedResult;
import com.sanish.movie_service.dtos.Movie.MovieDto;
import com.sanish.movie_service.entities.Movie;
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

import java.util.Optional;

@Service
@Transactional
public class MovieServiceImpl implements MovieService{

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
                .orElseThrow(() -> new ResourceNotFoundException("Movie", "movie-number", movieNumber));

        return fetchedMovie;
    }

    @Override
    public void addNewMovie(MovieDto movieDto) {

    }

    @Override
    public void updateMovieDetails(MovieDto movieDto) {

    }

    @Override
    public void deleteMovieById(Long id) {

    }
}
