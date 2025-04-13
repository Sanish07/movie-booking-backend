package com.sanish.movie_service.dtos.Showtime;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ShowtimeDto {

    private String showtimeNumber;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Double price;
}
