package com.nautestech.www;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties
@PropertySource("classpath:RecWeb.properties")
public class EnterpriseRec2020Application {

	private static Logger logger = LogManager.getLogger(EnterpriseRec2020Application.class);
			
	
	public static void main(String[] args) {
		logger.info("Start");
		SpringApplication app = new SpringApplication(EnterpriseRec2020Application.class);
		app.run(args);
	}

}
