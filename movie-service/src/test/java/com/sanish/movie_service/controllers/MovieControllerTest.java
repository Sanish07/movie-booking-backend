package com.sanish.movie_service.controllers;

import com.sanish.movie_service.AbstractIT;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

class MovieControllerTest extends AbstractIT {

    @Test
    void shouldReturnMovies(){
        given().contentType(ContentType.JSON)
                .when()
                .get("/api/movies")
                .then()
                .statusCode(200)
                .body("data", hasSize(3))
                .body("totalElements", is(3))
                .body("pageNumber", is(1))
                .body("totalPages", is(1))
                .body("isFirst", is(true))
                .body("isLast", is(true))
                .body("hasNext", is(false))
                .body("hasPrevious", is(false));
    }
}