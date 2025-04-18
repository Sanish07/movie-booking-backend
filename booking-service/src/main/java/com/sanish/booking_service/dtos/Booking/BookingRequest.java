package com.sanish.booking_service.dtos.Booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanish.booking_service.dtos.Ticket.TicketDto;
import com.sanish.booking_service.entities.Ticket;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.List;

public record BookingRequest(
        @Valid @NotEmpty(message = "Tickets cannot be empty") List<TicketDto> tickets,
        @JsonProperty("name") @NotBlank(message = "Customer name is required") String customerName,
        @JsonProperty("email") @NotBlank(message = "Customer email is required") @Email String customerEmail,
        @JsonProperty("phone") @NotBlank(message = "Customer phone number is required") String customerPhone,
        LocalDateTime bookingTime
){}
