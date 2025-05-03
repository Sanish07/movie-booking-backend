package com.sanish.notification_service.dtos.Consumers.BookingService;

import java.time.LocalDateTime;
import java.util.List;

//For new-bookings queue
public record BookingAddedEvent(
        String eventId,
        String bookingNumber,
        List<TicketDto> tickets,
        String customerName,
        String customerEmail,
        String customerPhone,
        LocalDateTime createdAt) {
}
