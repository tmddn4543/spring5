package com.nautestech.www.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
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
		List<Monitor> monitor = mService.getView(param);
		String str ="";
		for(int i=0; i<monitor.size(); i++) {
			str +="<div class='thumbnail standBy'>";
			str +="<div class='caption_head'>";
			str +="<h3>No."+(i+1)+"</h3>";
			str +="</div>";
			str +="<div class='caption'>";
			str +="<div class='caption_con1'>";
			str +="<p id='greetings_u_id'>"+monitor.get(i).getU_id()+"</p>";
			str +="<p id='greetings_stime'>"+monitor.get(i).getStime()+"</p>";
			str +="</div>";
			str +="<div class='caption_con2'>";
			str +="<p id='greetings_caller'>"+monitor.get(i).getCaller()+"</p>";
			str +="<i class='fa fa-arrow-down' aria-hidden='true'></i>";
			str +="<p id='greetings_called'>"+monitor.get(i).getCalled()+"</p>";
			str +="</div>";
			str +="</div>";
//			str +="<div class='caption_bottom'>";
//			str +="<button href='#' class='btn btn-primary' role='button'>";
//			str +="<i class='fa fa-volume-off' aria-hidden='true'></i>";
//			str +="청취가능";
//			str +="</button>";
//			str +="</div>";
			str +="</div>";
		}
		if(monitor.size()==0) {
			str +="<div style='position: absolute;left: 50%;top: 50%;transform: translate(-50%, -50%); text-align: center;'>";
			str +="<img src='/resource/assets/img/monitoring_fail.png' style='text-align: center; max-width: 100%;'>";
			str += "<p style='font-size: 23px;'>연결된 모니터링이 없습니다.</p>";
			str +="</div>";
		}
		messagingTemplate.convertAndSend("/topic/reply", str);
	}

}