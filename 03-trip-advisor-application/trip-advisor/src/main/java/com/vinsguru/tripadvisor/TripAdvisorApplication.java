package com.vinsguru.tripadvisor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration;

@SpringBootApplication
public class TripAdvisorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripAdvisorApplication.class, args);
	}

}
