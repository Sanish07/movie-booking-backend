package com.sanish.booking_service.clients.movie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
public class MovieServiceClient {
    private static final Logger log = LoggerFactory.getLogger(MovieServiceClient.class);
    private final RestClient restClient;

    @Autowired
    public MovieServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public Optional<ShowtimeResponse> getShowtimeByShowtimeNumber(String showtimeNumber){
        log.info("Calling movie-service to fetch showtime by number : " + showtimeNumber);
        var showtime_response = restClient
                .get()
                .uri("/api/showtimes/{showtimeNumber}", showtimeNumber)
                .retrieve()
                .body(ShowtimeResponse.class);

        return Optional.ofNullable(showtime_response);
    }
}
