package com.sanish.booking_service.dtos.Ticket;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;


public record TicketDto(
        @JsonProperty("code") @NotBlank(message = "Ticket Code is required") String ticketCode,
        @NotBlank(message = "Seat Number is required") String seatNumber,
        @DecimalMin(value = "0.0", inclusive = true, message = "Price is required, min value : 0.0") Double price) {}


