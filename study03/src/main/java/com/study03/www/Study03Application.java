package com.study03.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class Study03Application {

	public static void main(String[] args) {
		SpringApplication.run(Study03Application.class, args);
	}

}
