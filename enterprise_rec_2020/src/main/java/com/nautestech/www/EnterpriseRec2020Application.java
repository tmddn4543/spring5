package com.nautestech.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableConfigurationProperties
@PropertySource("classpath:RecWeb.properties")
public class EnterpriseRec2020Application {

	public static void main(String[] args) {
		SpringApplication.run(EnterpriseRec2020Application.class, args);
	}

}
