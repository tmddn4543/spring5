package com.nautestech.www.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nautestech.www.model.Monitor;
import com.nautestech.www.serviceImpl.MonitorService;


@Component
public class AutoController {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	MonitorService mService;
	
	
	@Scheduled(cron = "* * * * * *")
	public void job() {
		HashMap<String,Object> param = new HashMap<>();
		String message = "{\"name\":\"auto send data\"}";
		//String name = new Gson().fromJson(message, Map.class).get("name").toString();
		List<Monitor> monitor = mService.getView(param);
		
		messagingTemplate.convertAndSend("/topic/reply", message);
	}

}