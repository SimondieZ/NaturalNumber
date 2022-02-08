package com.trynumbers.attempt.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.trynumbers.attempt.repository.NumberRepository;
import com.trynumbers.attempt.entity.MyNumber;

@Configuration
public class LoadDatabase {
	
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
	
/*	@Bean
	CommandLineRunner initDatabase(NumberRepository repository) {
		return args -> {
			log.info("Preloading " + repository.save(new MyNumber(1, "I", "1", "The most common leading digit in many sets of data, a consequence of Benford's law.", new int[] {1})));
			log.info("Preloading " + repository.save(new MyNumber(2, "II", "2", "The third Fibonacci number, and the third and fifth Perrin numbers.", new int[] {1,2})));
			log.info("Preloading " + repository.save(new MyNumber(3, "III", "3", "The first unique prime due to the properties of its reciprocal.", new int[] {1,3})));
			log.info("Preloading " + repository.save(new MyNumber(4, "IV", "4", "The first positive non-Fibonacci number.",  new int[] {1,2,4})));
			log.info("Preloading " + repository.save(new MyNumber(5, "V", "5", "The third prime number.", new int[] {1,5})));
			log.info("Preloading " + repository.save(new MyNumber(6, "VI", "6", "The largest of the four all-Harshad numbers.",  new int[] {1,2,3,6})));
			log.info("Preloading " + repository.save(new MyNumber(7, "VII", "7", "The sum of any two opposite sides on a standard six-sided die.", new int[] {1,7})));
			log.info("Preloading " + repository.save(new MyNumber(8, "VIII", "8", "The largest cube in the Fibonacci sequence.", new int[] {1,2,4,8})));
			log.info("Preloading " + repository.save(new MyNumber(9, "IX", "9", "The highest single-digit number in the decimal system", new int[] {1,2,3,9})));
			log.info("Preloading " + repository.save(new MyNumber(10, "X", "10", "The smallest number whose status as a possible friendly number is unknown.", new int[] {1,2,5,10})));
		};
	}*/
}
