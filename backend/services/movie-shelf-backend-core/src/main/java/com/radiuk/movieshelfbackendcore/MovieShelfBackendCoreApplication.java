package com.radiuk.movieshelfbackendcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MovieShelfBackendCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieShelfBackendCoreApplication.class, args);
	}

}
