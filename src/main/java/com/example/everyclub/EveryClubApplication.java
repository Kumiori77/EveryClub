package com.example.everyclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EveryClubApplication {

	public static void main(String[] args) {
		SpringApplication.run(EveryClubApplication.class, args);
	}

}
