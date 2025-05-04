package com.sanish.notification_service.services;

import com.sanish.notification_service.dtos.Consumers.BookingService.BookingAddedEvent;
import com.sanish.notification_service.dtos.Consumers.BookingService.BookingCancelledEvent;
import com.sanish.notification_service.dtos.Consumers.BookingService.BookingErrorEvent;
import com.sanish.notification_service.dtos.Consumers.BookingService.BookingSuccessfulEvent;
import com.sanish.notification_service.entities.BookingEvent;
import com.sanish.notification_service.repositories.BookingEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/* Note(Important) :
         To Check Duplicate events processing(For BookingAddedEvent - new-bookings queue) :
          1. Visit the RabbitMQ Client.
          2. Go to exchanges tab -> Publish Message.
          3. Routing key: new-bookings
             Headers: content-type = application/json
             Payload : {
                            "customerName" : "Eoin Trace",
                            "customerEmail" : "trace@gmail.com",
                            "customerPhone" : "52154842515",
                            "bookingNumber" : "BO-001",
                            "eventId" : "EV-001",
                            "tickets" : [
                                {
                                    "code" : "T15",
                                    "seatNumber" : "S115",
                                    "price" : 504.25
                                }
                            ]
                        }
          4. Hit on Publish message 1 time and check NotificationServiceApplication log if event was consumed and mail was sent
          5. Click on publish message again and this time you should see "Received Duplicate BookingAddedEvent" warning in the
             NotificationServiceApplication log console

	*/

@Component
public class BookingEventsHandler {

    private static final Logger log = LoggerFactory.getLogger(BookingEventsHandler.class);

    private final NotificationService notificationService;
    private final BookingEventRepository bookingEventRepository;

    @Autowired
    public BookingEventsHandler(NotificationService notificationService, BookingEventRepository bookingEventRepository) {
        this.notificationService = notificationService;
        this.bookingEventRepository = bookingEventRepository;
    }

    @RabbitListener(queues = "${notifications.new-bookings-queue}") //SpEl property inside "" from application.yml
    void handleBookingAddedEvent(BookingAddedEvent event){ //Consuming RabbitMQ event from queue
        log.info("New Booking Added : {}", event);

        //If event was already consumed earlier, prevent sending notification again
        if(bookingEventRepository.existsByEventId(event.eventId())){
            log.warn("Received Duplicate BookingAddedEvent event with id : {}", event.eventId());
            return;
        }

        //Send Booking added email notification
        notificationService.sendBookingAddedNotification(event);

        //Persist the event in booking_events table
        BookingEvent newEvent = BookingEvent.builder()
                .eventId(event.eventId())
                .createdAt(LocalDateTime.now())
                .build();
        bookingEventRepository.save(newEvent);
    }

    @RabbitListener(queues = "${notifications.successful-bookings-queue}")
    void handleBookingSuccessfulEvent(BookingSuccessfulEvent event){
        log.info("Booking Successful : {}", event);

        //If event was already consumed earlier, prevent sending notification again
        if(bookingEventRepository.existsByEventId(event.eventId())){
            log.warn("Received Duplicate BookingSuccessfulEvent event with id : {}", event.eventId());
            return;
        }

        //Send Booking successful email notification
        notificationService.sendBookingSuccessfulNotification(event);

        //Persist the event in booking_events table
        BookingEvent newEvent = BookingEvent.builder()
                .eventId(event.eventId())
                .createdAt(LocalDateTime.now())
                .build();
        bookingEventRepository.save(newEvent);
    }

    @RabbitListener(queues = "${notifications.cancelled-bookings-queue}")
    void handleBookingCancelledEvent(BookingCancelledEvent event){
        log.info("Booking Cancelled : {}", event);

        //If event was already consumed earlier, prevent sending notification again
        if(bookingEventRepository.existsByEventId(event.eventId())){
            log.warn("Received Duplicate BookingCancelledEvent event with id : {}", event.eventId());
            return;
        }

        //Send Booking cancelled email notification
        notificationService.sendBookingCancelledNotification(event);

        //Persist the event in booking_events table
        BookingEvent newEvent = BookingEvent.builder()
                .eventId(event.eventId())
                .createdAt(LocalDateTime.now())
                .build();
        bookingEventRepository.save(newEvent);
    }

    @RabbitListener(queues = "${notifications.error-bookings-queue}")
    void handleBookingErrorEvent(BookingErrorEvent event){
        log.info("Error occurred while processing the booking : {}", event);

        //If event was already consumed earlier, prevent sending notification again
        if(bookingEventRepository.existsByEventId(event.eventId())){
            log.warn("Received Duplicate BookingErrorEvent event with id : {}", event.eventId());
            return;
        }

        //Send Booking error email notification to technical support team
        notificationService.sendBookingErrorEventNotificationToSupportTeam(event);

        //Persist the event in booking_events table
        BookingEvent newEvent = BookingEvent.builder()
                .eventId(event.eventId())
                .createdAt(LocalDateTime.now())
                .build();
        bookingEventRepository.save(newEvent);
    }
}
