package com.example.notification_service.consumer;

import com.example.notification_service.event.NotificationEvent;
import com.example.notification_service.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    private final EmailService emailService;

    public NotificationConsumer(EmailService emailService){
        this.emailService = emailService;
    }

    /* Kafka wants to listen message for "notification-group" and it's part of "notification-group" group id
    * This consumeEvent will listen to kafka & consume notificationEvent, once it will get data via
    * notificationEvent, will call sendMail from service using consumed data
    * */
    @KafkaListener(topics = "Notification-topic", groupId = "notification-group")
    public void consumeEvent(NotificationEvent notificationEvent){
        emailService.sendMail(notificationEvent.getEmail(), notificationEvent.getSubject(), notificationEvent.getMessage());
    }

}
