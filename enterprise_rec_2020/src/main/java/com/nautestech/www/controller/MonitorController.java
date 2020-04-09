package com.nautestech.www.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/monitor")
public class MonitorController {
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/monitor_page", method= {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model){
		String active = "active page_open";
		model.addAttribute("monitor_active", active);
		return "recording/monitor_page";
    }

}
