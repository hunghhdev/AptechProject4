package com.aptech.project.hotel.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspectJ {
    private static final Logger logger = LoggerFactory.getLogger(LoggerAspectJ.class);

    @Before("execution(* com.aptech.project.hotel.api.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Start API: {} ; Class: {} ", joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringType());
    }

    @After("execution(* com.aptech.project.hotel.api.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("Finish API: {} ; Class: {} ", joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringType());
    }

    @AfterReturning(pointcut = "execution(* com.aptech.project.hotel.api.*.*(..))",
            returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("after return API : {} ", joinPoint.getSignature().getName());
        logger.info("Method returned value is :  {}", result);
    }

    @AfterThrowing(pointcut = "execution(* com.aptech.project.hotel.api.*.*(..))",
            throwing = "error")
    public void logThrow(JoinPoint joinPoint, Throwable error) {
        logger.info("exception in API: {} ", joinPoint.getSignature().getName());
        logger.error("Exception is: {} ", error);
    }
}
