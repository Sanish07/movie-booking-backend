package com.sanish.booking_service.clients.movie;

import com.sanish.booking_service.ApplicationProperties;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;


@Configuration
public class MovieServiceClientConfig {

    @Bean
    RestClient restClient(ApplicationProperties applicationProperties, RestClient.Builder builder){

        //Configuring timeouts for RestClient
        ClientHttpRequestFactory clientRequestFactory = ClientHttpRequestFactoryBuilder.simple()
                .withCustomizer(customizer -> {
                    customizer.setConnectTimeout(Duration.ofSeconds(5));
                    customizer.setReadTimeout(Duration.ofSeconds(5));
                })
                .build();

        return RestClient.builder()
                .baseUrl(applicationProperties.getMovieServiceUrl())
                .requestFactory(clientRequestFactory)
                .build();
    }
}
