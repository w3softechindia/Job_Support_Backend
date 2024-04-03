package com.example.JobSupportBackend;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes=JobSupportBackendApplication.class)
class JobSupportBackendApplicationTests {

	@Test
	void contextLoads() {
		 // Perform additional assertions to verify the loaded context
        assertThat(true).isTrue(); // Example assertion, replace with your own
	}

}
