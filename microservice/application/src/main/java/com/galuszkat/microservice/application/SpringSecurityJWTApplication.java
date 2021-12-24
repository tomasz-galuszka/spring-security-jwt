package com.galuszkat.microservice.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.galuszkat")
public class SpringSecurityJWTApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJWTApplication.class, args);
	}

}