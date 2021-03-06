package com.simondiez.springnumbers.logging;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import com.simondiez.springnumbers.entity.NaturalNumber;

/**
 * Includes both AOP and logging functionality 
 *
 * @author Serafim Sokhin
 */
@Component
@Aspect
public class LoggingHandler {
	
	Logger log = LoggerFactory.getLogger(this.getClass());

    
    /**
     * Advice that logs when any method in @Repository, @Service or @Controller annotated class is entered and exited.
     *
     * @param joinPoint ProceedingJoinPoint for advice
     * @return target method result
     * @throws Throwable caught Exception 
     */
	@Around("LoggingPointcuts.applicationPackagePointcut() && LoggingPointcuts.springBeanPointcut()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		if (log.isDebugEnabled()) {
			log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
		}
		try {
			Object result = joinPoint.proceed();
			if (log.isDebugEnabled()) {
				log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
						joinPoint.getSignature().getName(), result);
				log.debug("With result: ");
				if (result instanceof List) {
					List<NaturalNumber> numbers = (List<NaturalNumber>) result;
					numbers.forEach(num -> log.debug(num.toString()));
				} else {
					log.debug("{}", result);
				}

			}
			return result;
		} catch (Exception e) {
			log.error("Exception in {}.{}() with type {}", joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(), e.getClass());
			log.error("Error message: {}", e.getMessage());

			List<StackTraceElement> stackTrace = Arrays.stream(e.getStackTrace()).collect(Collectors.toList());
			log.error("Exception stacktrace: ");
			stackTrace.forEach((methodCall) -> log.error(methodCall.toString()));

			throw e;
		}
	}
	
    
    /**
     * Advice that logs in detail when any method @Service annotated class is entered and exited.
     *
     * @param joinPoint ProceedingJoinPoint for advice
     * @return target method result
     * @throws Throwable caught Exception 
     */
	@Around("LoggingPointcuts.allServiceMethods()")
	public Object logAroundAllServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
		log.debug("------------------------------------------------------------");

		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		log.debug("Entering in Method :  " + methodSignature.getName());
		log.debug("Method signature: " + methodSignature.getMethod());
		log.debug("Argument[s] :  " + Arrays.toString(joinPoint.getArgs()));
		log.debug("Target class : " + joinPoint.getTarget().getClass().getName());

		try {
			log.debug("Method " + methodSignature.getName() + " is trying to start...");
			long begin = System.currentTimeMillis();
			Object targetMethodResult = joinPoint.proceed();
			long end = System.currentTimeMillis();

			if (!methodSignature.getName().equals("deleteMyNumber"))
				log.debug("The class name of the returned value : " + targetMethodResult.getClass());

			log.debug("Method " + methodSignature.getName() + " has completed successfully!");
			log.debug("Method execution time: " + (end - begin) + "ms");
			
			return targetMethodResult;
		} catch (Exception e) {
			log.warn("Method " + methodSignature.getName() + " has completed unsuccessfully!");
			throw e;
		} finally {
			log.debug("------------------------------------------------------------");
		}
	}
}
