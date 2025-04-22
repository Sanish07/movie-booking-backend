package com.sanish.booking_service.services;

import com.sanish.booking_service.clients.movie.MovieServiceClient;
import com.sanish.booking_service.clients.movie.ShowtimeResponse;
import com.sanish.booking_service.dtos.Booking.BookingRequest;
import com.sanish.booking_service.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MovieServiceValidator {
    private static final Logger log = LoggerFactory.getLogger(MovieServiceValidator.class);

    private final MovieServiceClient movieServiceClient;

    @Autowired
    public MovieServiceValidator(MovieServiceClient movieServiceClient) {
        this.movieServiceClient = movieServiceClient;
    }

    public void validate(BookingRequest bookingRequest) {
        String showtimeNumber = bookingRequest.showtimeNumber();

        ShowtimeResponse response = movieServiceClient
                .getShowtimeByShowtimeNumber(showtimeNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Showtime","showtimeNumber",showtimeNumber));

        log.info(response.toString());
    }
}
