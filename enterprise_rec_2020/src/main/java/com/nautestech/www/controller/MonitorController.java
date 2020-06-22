package com.nautestech.www.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/monitor")
public class MonitorController {
	
	private static Logger main_logger = LogManager.getLogger(MonitorController.class);
	
	@Secured({"ROLE_ADMIN","ROLE_OPERATIONADMIN","ROLE_GROUPADMIN","ROLE_LISTENUSER","ROLE_SMSUSER"})
	@RequestMapping(value = "/monitor_page", method= {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model){
		main_logger.info("welcome to Monitor_Page");
		return "recording/monitor_page";
    }

}
