package com.sanish.movie_service.dtos.Theater;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TheaterDto {
    private String theaterNumber;

    private String name;

    private String location;

    private Integer totalScreens;
}
