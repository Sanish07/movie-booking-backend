package com.sanish.notification_service.services;

import com.sanish.notification_service.ApplicationProperties;
import com.sanish.notification_service.dtos.Consumers.BookingService.BookingAddedEvent;
import com.sanish.notification_service.dtos.Consumers.BookingService.BookingCancelledEvent;
import com.sanish.notification_service.dtos.Consumers.BookingService.BookingErrorEvent;
import com.sanish.notification_service.dtos.Consumers.BookingService.BookingSuccessfulEvent;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final JavaMailSender emailSender;
    private final ApplicationProperties properties;

    @Autowired
    public NotificationService(JavaMailSender emailSender, ApplicationProperties properties) {
        this.emailSender = emailSender;
        this.properties = properties;
    }

    public void sendBookingAddedNotification(BookingAddedEvent event) {
        String message =
                """
                ===================================================
                Booking Added Notification
                ----------------------------------------------------
                Dear %s,
                Your Movie Booking with booking-number: %s has been received.
                Please wait for tickets confirmation email from the team.

                Thanks,
                MovieBooking Team
                ===================================================
                """
                        .formatted(event.customerName(),event.bookingNumber());
        log.info("\n{}", message);
        sendEmail(event.customerEmail(), "Booking Added/Received Notification", message);
    }

    public void sendBookingSuccessfulNotification(BookingSuccessfulEvent event) {
        String message =
                """
                ===================================================
                Booking Successful Notification
                ----------------------------------------------------
                Dear %s,
                Your Movie Booking with booking-number: %s has been confirmed!
                Please carry the e-tickets to ticket counter to receive theater tickets before show.

                Thanks,
                MovieBooking Team
                ===================================================
                """
                        .formatted(event.customerName(),event.bookingNumber());
        log.info("\n{}", message);
        sendEmail(event.customerEmail(), "Booking Successful Notification", message);
    }

    public void sendBookingCancelledNotification(BookingCancelledEvent event) {
        String message =
                """
                ===================================================
                Booking Cancelled Notification
                ----------------------------------------------------
                Dear %s,
                Your Movie Booking with booking-number: %s has been cancelled :(
                Reason: %s

                Thanks,
                MovieBooking Team
                ===================================================
                """
                        .formatted(event.customerName(),event.bookingNumber(), event.cancellationReason());
        log.info("\n{}", message);
        sendEmail(event.customerEmail(), "Booking Cancelled Notification", message);
    }

    public void sendBookingErrorEventNotificationToSupportTeam(BookingErrorEvent event) {
        String message =
                """
                ===================================================
                Booking Processing Failure Notification
                ----------------------------------------------------
                Hi MovieBooking Technical Team,
                The movie booking processing failed for bookingNumber: %s.
                Reason: %s

                Thanks,
                MovieBooking Notifications Team
                ===================================================
                """
                        .formatted(event.bookingNumber(), event.errorCause());
        log.info("\n{}", message);
        sendEmail("support.moviebooking@gmail.com", "Booking Processing Failure Notification", message);
    }

    private void sendEmail(String recipient, String subject, String content) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(properties.getSupportEmail());
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(content);
            emailSender.send(mimeMessage);
            log.info("Email sent to: {}", recipient);
        } catch (Exception e) {
            throw new RuntimeException("Error while sending email", e);
        }
    }
}
