package com.example.AgentApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.AgentApp"})
public class AgentAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgentAppApplication.class, args);
	}

}
