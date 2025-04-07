package com.sanish.movie_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
    String requestPath;
    HttpStatus statusCode;
    String message;
    LocalDateTime errorTime;
}
