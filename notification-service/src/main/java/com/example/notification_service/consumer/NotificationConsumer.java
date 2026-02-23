package com.example.notification_service.consumer;

import com.example.notification_service.event.NotificationEvent;
import com.example.notification_service.service.EmailService;
import com.example.notification_service.service.SmsService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    private final EmailService emailService;
    private final SmsService smsService;

    public NotificationConsumer(EmailService emailService, SmsService smsService){
        this.emailService = emailService;
        this.smsService = smsService;
    }

    /* Kafka wants to listen message for "notification-group" and it's part of "notification-group" group id
    * This consumeEvent will listen to kafka & consume notificationEvent, once it will get data via
    * notificationEvent, will call sendMail from service using consumed data
    * */
    @KafkaListener(topics = "Notification-topic", groupId = "notification-group")
    public void consumeEvent(NotificationEvent notificationEvent){
        if("EMAIL".equals(notificationEvent.getType())){
            emailService.sendMail(notificationEvent.getEmail(), notificationEvent.getSubject(), notificationEvent.getMessage());
        }
        else if ("SMS".equals(notificationEvent.getType())){
            smsService.sendSms(notificationEvent.getMobile(), notificationEvent.getMessage());
        }
    }

}
