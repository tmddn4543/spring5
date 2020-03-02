package com.study05.www;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class TestBoardApplication {
	
	private static Logger logger = LogManager.getLogger(TestBoardApplication.class);

	public static void main(String[] args) {
		logger.info("Start 인포");
		SpringApplication app = new SpringApplication(TestBoardApplication.class);
		app.run(args);
	}
}
