package com.sanish.notification_service.services;

import com.sanish.notification_service.dtos.Consumers.BookingService.BookingAddedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class BookingEventsHandler {

    private static final Logger log = LoggerFactory.getLogger(BookingEventsHandler.class);

    @RabbitListener(queues = "${notifications.new-bookings-queue}") //SpEl property inside "" from app.yml
    void handleBookingAddedEvent(BookingAddedEvent event){
        log.info("New Booking Added : {}", event);
    }
}
