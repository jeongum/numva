package com.egongil.numva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class NumvaApplication {

	public static void main(String[] args) {
		SpringApplication.run(NumvaApplication.class, args);
	}

}
