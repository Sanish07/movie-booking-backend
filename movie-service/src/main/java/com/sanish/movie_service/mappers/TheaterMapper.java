package com.sanish.movie_service.mappers;

import com.sanish.movie_service.dtos.Theater.TheaterDto;
import com.sanish.movie_service.entities.Theater;

public class TheaterMapper {

    public static TheaterDto mapToTheaterDto(Theater theater){
        return new TheaterDto(
                theater.getTheaterNumber(),
                theater.getName(),
                theater.getLocation(),
                theater.getTotalScreens()
        );
    }

    public static Theater mapToTheater(TheaterDto theaterDto){
        return Theater.builder()
                .theaterNumber(theaterDto.getTheaterNumber())
                .name(theaterDto.getName())
                .location(theaterDto.getLocation())
                .totalScreens(theaterDto.getTotalScreens())
                .build();
    }
}
