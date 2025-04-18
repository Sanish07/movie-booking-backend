package com.sanish.booking_service.controllers;

import com.sanish.booking_service.ApplicationProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rabbit-test")
public class RabbitDemoController {

    private final RabbitTemplate rabbitTemplate;
    private final ApplicationProperties applicationProperties;

    @Autowired
    public RabbitDemoController(RabbitTemplate rabbitTemplate, ApplicationProperties applicationProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.applicationProperties = applicationProperties;
    }

    @PostMapping("/send")
    public void sendMessageToQueue(@RequestBody MessageBody messageBody){
        rabbitTemplate.convertAndSend(
                applicationProperties.getBookingsEventsExchange(),
                messageBody.routingKey(),
                messageBody.payload()
        );
    }
}

record MessageBody(String routingKey, CustomPayload payload){}
record CustomPayload(String content){}
