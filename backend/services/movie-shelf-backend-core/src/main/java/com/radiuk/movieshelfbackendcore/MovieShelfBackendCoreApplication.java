package com.radiuk.movieshelfbackendcore;

import com.radiuk.movieshelfbackendcore.config.CloudinaryProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(CloudinaryProperties.class)
public class MovieShelfBackendCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieShelfBackendCoreApplication.class, args);
	}

}
