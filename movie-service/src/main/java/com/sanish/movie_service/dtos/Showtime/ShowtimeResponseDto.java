package com.sanish.movie_service.dtos.Showtime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ShowtimeResponseDto {

    @JsonProperty("show_details")
    private ShowtimeDto showtimeDto;

    private String movieTitle;

    private String theaterName;

    private Integer screenNumber;
}
