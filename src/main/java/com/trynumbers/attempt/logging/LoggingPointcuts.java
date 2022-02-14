package com.trynumbers.attempt.logging;

import org.aspectj.lang.annotation.Pointcut;

public class LoggingPointcuts {
		@Pointcut("within(@org.springframework.stereotype.Repository *)" +
	            " || within(@org.springframework.stereotype.Service *)" +
	            " || within(@org.springframework.web.bind.annotation.RestController *)")
	        public void springBeanPointcut() { }
	    
	    @Pointcut("within(com.trynumbers.attempt..*)" )
	        public void applicationPackagePointcut() { }
	    
		
		@Pointcut("execution(* com.trynumbers.attempt.service..*(..))")
			public void allServiceMethods() { }
		
}
