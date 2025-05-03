package com.sanish.booking_service.configurations.rabbit_jobs;

import com.sanish.booking_service.services.BookingEventService;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
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
    @SchedulerLock(name = "publishBookingEvents")//This annotation will serve the task to run the job and process all entries inside it only one time among all running application instances in cloud environment(Avoids duplicate processing)
    public void publishBookingEvents(){
        LockAssert.assertLocked();
        log.info("Publishing Booking Events at {}", Instant.now());
        bookingEventService.publishBookingEvents();
    }
}
