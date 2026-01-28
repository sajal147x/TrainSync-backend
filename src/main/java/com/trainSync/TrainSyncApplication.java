package com.trainSync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class TrainSyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainSyncApplication.class, args);
	}
	


}
