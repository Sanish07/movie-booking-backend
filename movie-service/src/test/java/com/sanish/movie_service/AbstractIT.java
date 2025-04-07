package com.sanish.movie_service;


import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

@Import(ContainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIT {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }
}
