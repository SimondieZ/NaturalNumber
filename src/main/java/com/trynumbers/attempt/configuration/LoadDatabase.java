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
	
	@Bean
	CommandLineRunner initDatabase(NumberRepository repository) {
		return args -> {
			log.info("Preloading " + repository.save(new MyNumber(1, "I", "1", "Наименьшее натуральное число")));
		    log.info("Preloading " + repository.save(new MyNumber(2, "II", "10", "Наименьшее и первое простое число, единственное чётное простое число")));
		};
	}
}
