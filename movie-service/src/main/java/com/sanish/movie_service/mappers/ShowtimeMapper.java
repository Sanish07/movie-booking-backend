package com.sanish.movie_service.mappers;

import com.sanish.movie_service.dtos.Showtime.ShowtimeDto;
import com.sanish.movie_service.entities.Showtime;

import java.math.BigDecimal;

public class ShowtimeMapper {

    public static ShowtimeDto mapToShowtimeDto(Showtime showtime){
        return new ShowtimeDto(
                showtime.getShowtimeNumber(),
                showtime.getStartTime(),
                showtime.getEndTime(),
                Double.parseDouble(showtime.getPrice().toString())
        );
    }

    public static Showtime mapToShowtime(ShowtimeDto showtimeDto){
        return Showtime.builder()
                .showtimeNumber(showtimeDto.getShowtimeNumber())
                .startTime(showtimeDto.getStartTime())
                .endTime(showtimeDto.getEndTime())
                .price(BigDecimal.valueOf(showtimeDto.getPrice()))
                .build();
    }
}
