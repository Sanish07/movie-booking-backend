package com.sanish.notification_service.dtos.Consumers.BookingService;


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
