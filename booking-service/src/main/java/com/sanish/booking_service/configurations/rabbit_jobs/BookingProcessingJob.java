package com.sanish.booking_service.configurations.rabbit_jobs;

import com.sanish.booking_service.services.BookingService;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class BookingProcessingJob {
    private static final Logger log = LoggerFactory.getLogger(BookingProcessingJob.class);

    private final BookingService bookingService;

    @Autowired
    public BookingProcessingJob(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    //Service will look for new bookings every 10 seconds in database table and process NEW status bookings
    @Scheduled(cron = "${bookings.new-bookings-processing-job-cron}")
    @SchedulerLock(name = "processNewBookings")//This annotation will serve the task to run the job and process all entries inside it only one time among all running application instances in cloud environment(Avoids duplicate processing)
    public void processNewBookings(){
        LockAssert.assertLocked();
        log.info("Processing New Booking Events at {}", Instant.now());
        bookingService.processNewBookings();
    }
}
