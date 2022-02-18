package com.trynumbers.attempt.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.trynumbers.attempt.repository.NumberRepository;
import com.trynumbers.attempt.repository.UserRepository;
import com.trynumbers.attempt.security.Role;
import com.trynumbers.attempt.security.Status;
import com.trynumbers.attempt.entity.NaturalNumber;
import com.trynumbers.attempt.entity.User;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(NumberRepository numberRepository, UserRepository userRepository) {
		return args -> {
			if (numberRepository.findAll().isEmpty()) {
				log.info("Preloading " + numberRepository.save(new NaturalNumber.NumberBuilder()
						.name(1).romaNotation("I").binaryNotation("1")
						.description("The most common leading digit in many sets of data, a consequence of Benford's law.")
						.divisors(new int[] { 1 }).build()));
				log.info("Preloading " + numberRepository
						.save(new NaturalNumber.NumberBuilder()
								.name(2).romaNotation("II").binaryNotation("10")
								.description("The third Fibonacci number, and the third and fifth Perrin numbers.")
								.divisors(new int[] { 1, 2 }).build()));
				log.info("Preloading " + numberRepository
						.save(new NaturalNumber.NumberBuilder()
								.name(3).romaNotation("III").binaryNotation("11")
								.description("The first unique prime due to the properties of its reciprocal.")
								.divisors(new int[] { 1, 3 }).build()));
				log.info("Preloading " + numberRepository
						.save(new NaturalNumber.NumberBuilder()
								.name(4).romaNotation("IV")
								.binaryNotation("100").description("The first positive non-Fibonacci number.")
								.divisors(new int[] { 1, 2, 4 }).build()));
				log.info("Preloading " + numberRepository
						.save(new NaturalNumber.NumberBuilder()
								.name(5).romaNotation("V").binaryNotation("101")
								.description("The third prime number.")
								.divisors(new int[] { 1, 5 }).build()));
				log.info("Preloading " + numberRepository
						.save(new NaturalNumber.NumberBuilder()
								.name(6).romaNotation("VI").binaryNotation("110")
								.description("The largest of the four all-Harshad numbers.")
								.divisors(new int[] { 1, 2, 3, 6 }).build()));
				log.info("Preloading " + numberRepository
						.save(new NaturalNumber.NumberBuilder()
								.name(7).romaNotation("VII").binaryNotation("111")
								.description("The sum of any two opposite sides on a standard six-sided die.")
								.divisors(new int[] { 1, 7 }).build()));
				log.info("Preloading " + numberRepository
						.save(new NaturalNumber.NumberBuilder()
								.name(8).romaNotation("VIII").binaryNotation("1000")
								.description("The largest cube in the Fibonacci sequence.")
								.divisors(new int[] { 1, 2, 4, 8 }).build()));
				log.info("Preloading " + numberRepository
						.save(new NaturalNumber.NumberBuilder()
								.name(9).romaNotation("IX").binaryNotation("1001")
								.description("The highest single-digit number in the decimal system.")
								.divisors(new int[] { 1, 3, 9 }).build()));
				log.info("Preloading " + numberRepository
						.save(new NaturalNumber.NumberBuilder()
								.name(10)
								.romaNotation("X").binaryNotation("1010")
								.description("The smallest number whose status as a possible friendly number is unknown.")
								.divisors(new int[] { 1, 2, 5, 10 }).build()));
			}
			
			if (userRepository.findAll().isEmpty()) {
				log.info("Preloading " + userRepository
						.save(new User("admin@gmail.com", "$2a$12$Ynq7H74TUa9IKinHdwd0AOcxy3fnSkYrRftyu93nRRSyNmc4w76Zq",
								"Admin", "Adminov", Role.ADMIN, Status.ACTIVE)));
				log.info("Preloading " + userRepository
						.save(new User("user@gmail.com", "$2a$12$65KN659/dtfOUEQvl/tVY.gUZy0chMR2ZzwIZ9jkFT0g/vh4kFwlu",
								"User", "Userov", Role.USER, Status.ACTIVE)));
			}
		};
	}
}
