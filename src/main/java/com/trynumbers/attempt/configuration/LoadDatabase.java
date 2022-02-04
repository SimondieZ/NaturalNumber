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
//			log.info("Preloading " + repository.save(new MyNumber(1, "I", "1", "Тупа первый")));
//		    log.info("Preloading " + repository.save(new MyNumber(2, "II", "10", "Первое четное число")));
//			log.info("Preloading " + repository.save(new MyNumber(3, "III", "11", "Вечно третий")));
//		    log.info("Preloading " + repository.save(new MyNumber(4, "IV", "100", "Просто четырка")));
		};
	}*/
}
