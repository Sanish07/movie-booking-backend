package com.sanish.booking_service.clients.movie;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record ShowtimeResponse(
        @JsonProperty("show_details") ShowBody showBody,
        String movieTitle,
        String theaterName,
        Integer screenNumber){
}

record ShowBody(
        String showtimeNumber,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double price
){}
