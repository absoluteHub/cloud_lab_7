package org.example.cloud_lab_7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CloudLab7Application {

	public static void main(String[] args) {
		SpringApplication.run(CloudLab7Application.class, args);
	}

}
