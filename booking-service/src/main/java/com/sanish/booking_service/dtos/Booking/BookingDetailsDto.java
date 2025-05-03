package com.sanish.booking_service.dtos.Booking;

import com.sanish.booking_service.entities.BookingStatus;
import com.sanish.booking_service.entities.Ticket;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record BookingDetailsDto(
        String showtimeNumber,
        String bookingNumber,
        String username,
        String customerName,
        String customerEmail,
        String customerPhone,
        Double totalPrice,
        BookingStatus status,
        LocalDateTime bookingTime,
        List<Ticket> tickets) {
}
