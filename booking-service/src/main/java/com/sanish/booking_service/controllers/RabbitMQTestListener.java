package com.sanish.booking_service.controllers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

//Test if queues are working with this class as queue handler
//@Service
public class RabbitMQTestListener {

    @RabbitListener(queues = "${bookings.new-bookings-queue}")
    public void handleNewBookingsQueue(CustomPayload customPayload){
        System.out.println("New Booking : "+customPayload.content());
    }

    @RabbitListener(queues = "${bookings.successful-bookings-queue}")
    public void handleSuccessfulBookingsQueue(CustomPayload customPayload){
        System.out.println("Booking Successful : "+customPayload.content());
    }

}
