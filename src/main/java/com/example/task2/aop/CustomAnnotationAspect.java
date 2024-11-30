package com.example.task2.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class CustomAnnotationAspect {
    private static final Logger logger = LoggerFactory.getLogger(CustomAnnotationAspect.class);
    @Around("@annotation(LogMethodParam)")
    public Object logMethodParameters(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Method called: " + joinPoint.getSignature().getName());
        logger.info("Parameters: " + Arrays.toString(joinPoint.getArgs()));
        return joinPoint.proceed();
    }
}
