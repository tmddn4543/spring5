package com.nautestech.www.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nautestech.www.model.Users;
import com.nautestech.www.serviceImpl.UsersService;



@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	UsersService uService;
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/user_page", method= {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) throws JsonProcessingException{
		HashMap<String, Object> param = new HashMap<>();
		String active = "active page_open";
		model.addAttribute("user_active", active);
		return "recording/user_page";
    }
	
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/user_page_ajax", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<Users> user_page_ajax(Model model) throws JsonProcessingException{
		HashMap<String, Object> param = new HashMap<>();
		String active = "active page_open";
		model.addAttribute("user_active", active);
		return uService.getView(param);
    }
	
}
