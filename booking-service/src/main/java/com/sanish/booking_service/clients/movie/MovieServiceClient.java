package com.sanish.booking_service.clients.movie;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

//To test circuit breaker and resilience patterns, simulate slow calls in ShowtimeController in movie-service
@Component
public class MovieServiceClient {
    private static final Logger log = LoggerFactory.getLogger(MovieServiceClient.class);
    private final RestClient restClient;

    @Autowired
    public MovieServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    // By default the resilience pattern running order is Bulkhead -> TimeLimiter -> RateLimiter -> CircuitBreaker -> Retry, we can change this aspect order in application.yml file
    //Retry annotation from resilience4j will retry calling the service as many times configured or default(3) times
    //name attribute in @Retry is called backend name, set number of retries in application.properties or .yml
    @CircuitBreaker(name = "movie-service")
    @Retry(name = "movie-service", fallbackMethod = "getShowtimeFallback")
    public Optional<ShowtimeResponse> getShowtimeByShowtimeNumber(String showtimeNumber){
        log.info("Calling movie-service to fetch showtime by number : " + showtimeNumber);

            var showtime_response = restClient
                    .get()
                    .uri("/api/showtimes/{showtimeNumber}", showtimeNumber)
                    .retrieve()
                    .body(ShowtimeResponse.class);

            return Optional.ofNullable(showtime_response);

    }

    public Optional<ShowtimeResponse> getShowtimeFallback(String showtimeNumber, Throwable err){
        System.out.println("[Fallback Invoked]MovieServiceClient.getShowtimeFallback for showtimeNumber : " + showtimeNumber);
        return Optional.empty();
    }
}
