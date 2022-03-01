package com.simondiez.springnumbers.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenApi() {
		return new OpenAPI()
				.info(new Info().title("Natural Numbers API").version("v.1.0.0")
						.license(new License().name("Apache 2.0").url("http://springdoc.org"))
						.contact(new Contact().name("Serafim").email("serafimsohin@gmail.com")))
				.servers(List.of(new Server().url("http://localhost:8080").description("Dev service")));
	}
}
