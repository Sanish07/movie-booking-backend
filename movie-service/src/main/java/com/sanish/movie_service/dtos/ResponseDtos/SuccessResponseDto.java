package com.sanish.movie_service.dtos.ResponseDtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessResponseDto {
    String statusCode;
    String statusMessage;
}
