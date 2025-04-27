package com.sanish.booking_service.configurations.rabbit_jobs;

import com.sanish.booking_service.services.BookingEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class BookingEventsPublishingJob {
    public static final Logger log = LoggerFactory.getLogger(BookingEventsPublishingJob.class);

    public final BookingEventService bookingEventService;

    @Autowired
    public BookingEventsPublishingJob(BookingEventService bookingEventService) {
        this.bookingEventService = bookingEventService;
    }

    //This scheduled publishing job method will run every 5 seconds
    @Scheduled(cron = "${bookings.publish-booking-events-job-cron}")
    public void publishBookingEvents(){
        log.info("Publishing Booking Events at {}", Instant.now());
        bookingEventService.publishBookingEvents();
    }
}
