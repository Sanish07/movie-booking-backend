package com.sanish.booking_service.clients.movie;

import com.sanish.booking_service.ApplicationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class MovieServiceClientConfig {

    @Bean
    RestClient restClient(ApplicationProperties applicationProperties){
        return RestClient.builder()
                .baseUrl(applicationProperties.getMovieServiceUrl())
                .build();
    }
}
