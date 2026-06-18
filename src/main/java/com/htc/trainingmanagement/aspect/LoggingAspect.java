package com.htc.trainingmanagement.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


@Aspect
@Component
@Slf4j

public class LoggingAspect {

	@Pointcut("execution(* com.htc.trainingmanagement.controller..*(..)) "
//        + "|| " +
//        "execution(* com.htc.deploymentmanagement.serviceImpl..*(..))"
	)
	public void applicationMethods() {
	}

	@Before("applicationMethods()")
	public void logBefore(JoinPoint joinPoint) {
		// log.info("Excected By Raj Obli Raj");
		log.info("Entering Method: {}", joinPoint.getSignature().toShortString());

		log.info("Arguments: {}", Arrays.toString(joinPoint.getArgs()));
	}

	@AfterReturning(pointcut = "applicationMethods()", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {

		log.info("Method {} executed successfully", joinPoint.getSignature().getName());

		log.info("Returned Value: {}", result);
	}

	@AfterThrowing(pointcut = "applicationMethods()", throwing = "exception")
	public void logException(JoinPoint joinPoint, Throwable exception) {

		log.error("Exception in method {} : {}", joinPoint.getSignature().getName(), exception.getMessage(), exception);
	}
}