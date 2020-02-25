package com.study04.www.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
	
	@RequestMapping(value = "/", method= {RequestMethod.GET, RequestMethod.POST})
    public String loginPage()
	{
		return "utime/index";
    }
}
