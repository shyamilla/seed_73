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

// Marks this class as an Aspect (contains cross-cutting logic)
@Aspect

@Component

// Creates an SLF4J logger named 'log'
@Slf4j
public class LoggingAspect {

	// Pointcut to intercept all methods inside the controller package
	@Pointcut("execution(* com.htc.trainingmanagement.controller..*(..))")
	// + "|| execution(* com.htc.deploymentmanagement.serviceImpl..*(..))")
	public void applicationMethods() {
	}

	// Executes before the intercepted method is called
	@Before("applicationMethods()")
	public void logBefore(JoinPoint joinPoint) {

		// Logs the method name being executed
		log.info("Entering Method: {}", joinPoint.getSignature().toShortString());

		// Logs the arguments passed to the method
		log.info("Arguments: {}", Arrays.toString(joinPoint.getArgs()));
	}

	// Executes after the method completes successfully
	@AfterReturning(pointcut = "applicationMethods()", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {

		// Logs successful method execution
		log.info("Method {} executed successfully", joinPoint.getSignature().getName());

		// Logs the returned value
		log.info("Returned Value: {}", result);
	}

	// Executes if the intercepted method throws an exception
	@AfterThrowing(pointcut = "applicationMethods()", throwing = "exception")
	public void logException(JoinPoint joinPoint, Throwable exception) {

		// Logs the exception details
		log.error("Exception in method {} : {}",
				joinPoint.getSignature().getName(),
				exception.getMessage(),
				exception);
	}
}