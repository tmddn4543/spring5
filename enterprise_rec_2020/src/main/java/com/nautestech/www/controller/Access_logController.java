package com.nautestech.www.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/access_log")
public class Access_logController {
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/access_log_page", method= {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model){
		String active = "active page_open";
		model.addAttribute("access_log_active", active);
		
		return "recording/access_log_page";
    }
}
