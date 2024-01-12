package com.ragnarson.StudentMVC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class StudentMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentMvcApplication.class, args);
	}

}
