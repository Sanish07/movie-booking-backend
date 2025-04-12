package com.sanish.movie_service.dtos.Screen;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sanish.movie_service.entities.Theater;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScreenDto {
    private Integer screenNumber;
    private Integer capacity;
}
