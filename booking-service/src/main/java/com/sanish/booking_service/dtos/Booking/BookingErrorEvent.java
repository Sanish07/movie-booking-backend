package com.sanish.booking_service.dtos.Booking;

import com.sanish.booking_service.dtos.Ticket.TicketDto;

import java.time.LocalDateTime;
import java.util.List;

public record BookingErrorEvent(
        String eventId,
        String bookingNumber,
        List<TicketDto> tickets,
        String customerName,
        String customerEmail,
        String customerPhone,
        LocalDateTime createdAt,
        String errorCause) {
}
