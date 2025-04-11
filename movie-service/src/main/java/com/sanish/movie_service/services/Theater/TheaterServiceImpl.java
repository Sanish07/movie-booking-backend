package com.sanish.movie_service.services.Theater;

import com.sanish.movie_service.dtos.ResponseDtos.PagedResult;
import com.sanish.movie_service.dtos.Theater.TheaterDto;
import com.sanish.movie_service.entities.Theater;
import com.sanish.movie_service.exceptions.ResourceAlreadyExistsException;
import com.sanish.movie_service.exceptions.ResourceNotFoundException;
import com.sanish.movie_service.mappers.TheaterMapper;
import com.sanish.movie_service.repositories.TheaterRepository;
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
public class TheaterServiceImpl implements TheaterService{

    private TheaterRepository theaterRepository;

    @Autowired
    public TheaterServiceImpl(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    @Override
    public PagedResult<TheaterDto> getAllTheaters(Integer pageNumber) {
        Sort sortFilter = Sort.by("name").ascending();
        pageNumber = (pageNumber <= 1) ? 0 : pageNumber - 1;
        Pageable pageable = PageRequest.of(pageNumber,10, sortFilter);

        Page<TheaterDto> fetchedTheaterList = theaterRepository
                .findAll(pageable).map(TheaterMapper::mapToTheaterDto);

        PagedResult<TheaterDto> theaterPagedResult = new PagedResult<>(
                fetchedTheaterList.getContent(),
                fetchedTheaterList.getTotalElements(),
                fetchedTheaterList.getNumber() + 1,
                fetchedTheaterList.getTotalPages(),
                fetchedTheaterList.isFirst(),
                fetchedTheaterList.isLast(),
                fetchedTheaterList.hasNext(),
                fetchedTheaterList.hasPrevious()
        );

        return theaterPagedResult;
    }

    @Override
    public TheaterDto getTheaterByTheaterNumber(String theaterNumber) {
        TheaterDto fetchedTheater = theaterRepository.findByTheaterNumber(theaterNumber)
                .map(TheaterMapper::mapToTheaterDto)
                .orElseThrow(() -> new ResourceNotFoundException("Theater","theaterNumber",theaterNumber));

        return fetchedTheater;
    }

    @Override
    public void addNewTheater(TheaterDto theaterDto) {
        Optional<Theater> fetchedTheater = theaterRepository
                .findByTheaterNumber(theaterDto.getTheaterNumber());

        if(fetchedTheater.isPresent()){
            throw new ResourceAlreadyExistsException("Theater","theaterNumber",theaterDto.getTheaterNumber());
        }

        Theater convertedTheaterObj = TheaterMapper.mapToTheater(theaterDto);
        convertedTheaterObj.setCreatedAt(LocalDateTime.now());
        convertedTheaterObj.setUpdatedAt(LocalDateTime.now());

        theaterRepository.save(convertedTheaterObj);
    }

    @Override
    public void updateTheaterDetails(TheaterDto theaterDto) {
        Optional<Theater> fetchedTheater = theaterRepository
                .findByTheaterNumber(theaterDto.getTheaterNumber());

        if(fetchedTheater.isEmpty()){
            throw new ResourceNotFoundException("Theater","theaterNumber", theaterDto.getTheaterNumber());
        }

        Theater convertedTheaterObj = TheaterMapper.mapToTheater(theaterDto);
        convertedTheaterObj.setId(fetchedTheater.get().getId());
        convertedTheaterObj.setCreatedAt(fetchedTheater.get().getCreatedAt());
        convertedTheaterObj.setUpdatedAt(LocalDateTime.now());

        theaterRepository.save(convertedTheaterObj);
    }

    @Override
    public void deleteTheaterByTheaterNumber(String theaterNumber) {
        Optional<Theater> fetchedTheater = theaterRepository.findByTheaterNumber(theaterNumber);

        if(fetchedTheater.isEmpty()){
            throw new ResourceNotFoundException("Theater","theaterNumber",theaterNumber);
        }

        theaterRepository.deleteById(fetchedTheater.get().getId());
    }
}
