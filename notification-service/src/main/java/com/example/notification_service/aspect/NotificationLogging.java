package com.example.notification_service.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class NotificationLogging {

    @Before("within(com.example.notification_service)")
    public void beforeMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        log.info("------ Entering in method: {} of class: {} ------", methodName, className);
    }

    @After("within(com.example.notification_service)")
    public void afterMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.info("----- Exiting this method: {} of class: {} -----", methodName, className);
    }

    @AfterThrowing(value = "within(com.example.notification_service)", throwing = "exception")
    public void afterThrowingMethod(JoinPoint joinPoint, Throwable exception){
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.error("----- Method {} failed! -----", methodName);
        log.error("----- Exception Type: {} -----", exception.getClass().getName());
        log.error("----- Error Message: {} -----", exception.getMessage());
    }

}
