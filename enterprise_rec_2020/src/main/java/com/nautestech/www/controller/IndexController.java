package com.nautestech.www.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

	@Value("${webLoginLogo}")
	String webLoginLogo;
	
	@RequestMapping(value = "/", method= {RequestMethod.GET, RequestMethod.POST})
    public String loginPage(Model model){
		model.addAttribute("webLoginLogo",webLoginLogo);
		return "utime/login";
    }
}
