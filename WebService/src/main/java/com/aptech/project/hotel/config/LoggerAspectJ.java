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

    @Before("execution(* com.aptech.project.hotel.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Start controller: " + joinPoint.getSignature().getName());
    }

    @After("execution(* com.aptech.project.hotel.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("Finish controller: " + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.aptech.project.hotel.*.*(..))",
            returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("after return controller : " + joinPoint.getSignature().getName());
        logger.info("Method returned value is : " + result);
    }

    @AfterThrowing(pointcut = "execution(* com.aptech.project.hotel." +
            "*.*(..))",
            throwing = "error")
    public void logThrow(JoinPoint joinPoint, Throwable error) {
        logger.info("exception in controller: " + joinPoint.getSignature().getName());
        logger.error("Exception is: " + error);
    }
}
