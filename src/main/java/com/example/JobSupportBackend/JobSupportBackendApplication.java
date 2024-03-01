package com.example.JobSupportBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication

public class JobSupportBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobSupportBackendApplication.class, args);
	}
	// Configure MultipartResolver bean
    

}
