package com.nautestech.www.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class AutoController {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	
	
	
	@Scheduled(cron = "* * * * * *")
	public void job() {
		System.err.println("job!!");
		String message = "{\"name\":\"auto send data\"}";
		//String name = new Gson().fromJson(message, Map.class).get("name").toString();
		String name = "";
		messagingTemplate.convertAndSend("/topic/reply", name);
	}

}