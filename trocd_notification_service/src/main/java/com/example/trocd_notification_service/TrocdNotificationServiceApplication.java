package com.example.trocd_notification_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableMongoAuditing
public class TrocdNotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrocdNotificationServiceApplication.class, args);
	}

}
