package com.sanish.booking_service.services;

import com.sanish.booking_service.ApplicationProperties;
import com.sanish.booking_service.dtos.Booking.BookingAddedEvent;
import com.sanish.booking_service.dtos.Booking.BookingCancelledEvent;
import com.sanish.booking_service.dtos.Booking.BookingErrorEvent;
import com.sanish.booking_service.dtos.Booking.BookingSuccessfulEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(BookingEventPublisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final ApplicationProperties applicationProperties;

    @Autowired
    public BookingEventPublisher(RabbitTemplate rabbitTemplate, ApplicationProperties applicationProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.applicationProperties = applicationProperties;
    }

    public void publish(BookingAddedEvent bookingAddedEvent) {
        this.send(applicationProperties.getNewBookingsQueue(), bookingAddedEvent);
    }

    public void publish(BookingSuccessfulEvent bookingSuccessfulEvent) {
        this.send(applicationProperties.getSuccessfulBookingsQueue(), bookingSuccessfulEvent);
    }

    public void publish(BookingCancelledEvent bookingCancelledEvent) {
        this.send(applicationProperties.getCancelledBookingsQueue(), bookingCancelledEvent);
    }

    public void publish(BookingErrorEvent bookingErrorEvent){
        this.send(applicationProperties.getErrorBookingsQueue(), bookingErrorEvent);
    }

    private void send(String routingKey, Object payload){
        System.out.println("Publishing New/Pending Events... ");
        log.info("Exchange name : {} ", applicationProperties.getBookingsEventsExchange());
        log.info("Key : {} ",routingKey);
        log.info("Payload : {} ",payload.toString());
        rabbitTemplate.convertAndSend(
                applicationProperties.getBookingsEventsExchange(),
                routingKey,
                payload);
    }


}
