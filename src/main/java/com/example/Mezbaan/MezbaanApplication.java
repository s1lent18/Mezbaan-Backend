package com.example.Mezbaan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MezbaanApplication {

	public static void main(String[] args) {
		SpringApplication.run(MezbaanApplication.class, args);
	}

}
