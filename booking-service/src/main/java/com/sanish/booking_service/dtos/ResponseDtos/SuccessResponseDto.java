package com.sanish.booking_service.dtos.ResponseDtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class SuccessResponseDto {
    String statusCode;
    String statusMessage;
}
