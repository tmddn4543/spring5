package com.study05.www.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.study05.www.serviceImpl.BoardService;

@Controller
public class IndexController {
	
	@Autowired
	BoardService bService;
	
	
	@RequestMapping(value = "/", method= {RequestMethod.GET, RequestMethod.POST})
    public String loginPage()
	{
		return "utime/index";
    }
}
	
	
